package ir.mohsenafshar.apps.mytmdb.presentation.ui.bookmark

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

const val bookmarkNavigationRoute = "bookmark_route"

private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/bookmark/"

fun NavController.navigateToBookmarkScreen(navOptions: NavOptions? = null) {
    this.navigate("bookmark_route", navOptions)
}

fun NavGraphBuilder.bookmarkScreen() {
    composable(
        route = bookmarkNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN }
        ),
    ) {
        BookmarkScreen()
    }
}

@Composable
fun BookmarkScreen() {
    Text(text = "BookmarkScreen")
}