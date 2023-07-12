package ir.mohsenafshar.apps.mytmdb.presentation.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

const val NAV_MOVIE_DETAIL= "NAV_MOVIE_DETAIL"
const val detailNavigationRoute = "detail_route/{$NAV_MOVIE_DETAIL}"

private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/detail/{$NAV_MOVIE_DETAIL}"

fun NavController.navigateToDetailScreen(movieId: Int? = null, navOptions: NavOptions? = null) {
    this.navigate("detail_route/$movieId", navOptions)
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = detailNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN }
        ),
        arguments = listOf(
            navArgument(NAV_MOVIE_DETAIL) { type = NavType.IntType }
        ),
    ) {
        val movieId = it.arguments?.getInt(NAV_MOVIE_DETAIL)
        DetailScreen(movieId)
    }
}

@Composable
fun DetailScreen(movieId: Int?) {
    Text(text = "DetailScreen $movieId")
}