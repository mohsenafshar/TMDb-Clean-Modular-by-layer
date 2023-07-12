package ir.mohsenafshar.apps.mytmdb.presentation.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

const val NAV_SEARCH_QUERY = "navSearchQuery"
const val searchNavigationRoute = "search_route?query={$NAV_SEARCH_QUERY}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/search/{$NAV_SEARCH_QUERY}"

fun NavController.navigateToSearchScreen(searchQuery: String = "", navOptions: NavOptions? = null) {
    this.navigate("search_route?query=$searchQuery", navOptions)
}

fun NavGraphBuilder.searchScreen() {
    composable(
        route = searchNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN }
        ),
        arguments = listOf(
            navArgument(NAV_SEARCH_QUERY) {
                type = NavType.StringType
                defaultValue = ""
            }
        ),
    ) {
        val searchQuery = it.arguments?.getString(NAV_SEARCH_QUERY)
        SearchScreen(searchQuery)
    }
}

@Composable
fun SearchScreen(searchQuery: String?) {
    Text(text = "SearchScreen $searchQuery")
}