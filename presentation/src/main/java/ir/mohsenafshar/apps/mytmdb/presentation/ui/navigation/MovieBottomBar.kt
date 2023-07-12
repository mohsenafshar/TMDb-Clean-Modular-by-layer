package ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import ir.mohsenafshar.apps.mytmdb.presentation.ui.bookmark.bookmarkNavigationRoute
import ir.mohsenafshar.apps.mytmdb.presentation.ui.home.homeNavigationGraph
import ir.mohsenafshar.apps.mytmdb.presentation.ui.search.searchNavigationRoute

@Composable
fun MovieBottomNavigation(onDestinationChanged: (TopLevelDestination) -> Unit, currentRoute: String, modifier: Modifier = Modifier, currentDestination: NavDestination?) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    NavigationBar(
        modifier = modifier
    ) {
        topLevelDestinations.forEach { destination ->
            MovieNavigationBarItem(
                selected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(destination.iconTextId))
                },
                onClick = {
                    onDestinationChanged(destination)
                }
            )
        }
    }
}

@Composable
fun RowScope.MovieNavigationBarItem(
    selected: Boolean,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        icon = if (selected) selectedIcon else icon,
        label = label,
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        alwaysShowLabel = alwaysShowLabel,
    )
}

private fun TopLevelDestination.toRoute(): String {
    return when (this) {
        TopLevelDestination.HOME -> homeNavigationGraph // careful! we should use route of graph instead of route of start destination
        TopLevelDestination.SEARCH -> searchNavigationRoute
        TopLevelDestination.BOOKMARK -> bookmarkNavigationRoute
        TopLevelDestination.PROFILE -> ""
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route == destination.toRoute()
    } ?: false
