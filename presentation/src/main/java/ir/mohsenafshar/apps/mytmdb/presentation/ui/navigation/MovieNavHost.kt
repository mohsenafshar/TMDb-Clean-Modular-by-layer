package ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import androidx.navigation.navigation
import ir.mohsenafshar.apps.mytmdb.presentation.ui.bookmark.bookmarkScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.bookmark.navigateToBookmarkScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.detail.detailScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.detail.navigateToDetailScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.home.homeGraph
import ir.mohsenafshar.apps.mytmdb.presentation.ui.home.homeNavigationRoute
import ir.mohsenafshar.apps.mytmdb.presentation.ui.home.homeScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.home.navigateToHomeScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.TopLevelDestination.BOOKMARK
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.TopLevelDestination.HOME
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.TopLevelDestination.PROFILE
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.TopLevelDestination.SEARCH
import ir.mohsenafshar.apps.mytmdb.presentation.ui.search.navigateToSearchScreen
import ir.mohsenafshar.apps.mytmdb.presentation.ui.search.searchScreen


@Composable
fun MovieNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(route = "root_graph", navController = navController, startDestination = "home_graph", modifier = modifier) {
        homeGraph {
            homeScreen(
                showDetailScreen = {
                    navController.navigateToDetailScreen(1)
                }, onSearchTriggered = { movieSearchQuery ->
                    with(navController) {
                        navigateToSearchScreen(movieSearchQuery, topLevelNavOptions)
                    }
                })

            detailScreen()
        }

        searchScreen()
        bookmarkScreen()
    }
}

private val NavHostController.topLevelNavOptions: NavOptions
    get() = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

private val NavHostController.topLevelNavOptionsWithNewState: NavOptions
    get() = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = false
    }

fun NavHostController.navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    when (topLevelDestination) {
        HOME -> navigateToHomeScreen(navOptions = topLevelNavOptions)
        SEARCH -> navigateToSearchScreen(navOptions = topLevelNavOptions)
        BOOKMARK -> navigateToBookmarkScreen(navOptions = topLevelNavOptions)
        PROFILE -> TODO()
    }
}