package ir.mohsenafshar.apps.mytmdb.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.MovieBottomNavigation
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.MovieNavHost
import ir.mohsenafshar.apps.mytmdb.presentation.ui.navigation.navigateToTopLevelDestination
import ir.mohsenafshar.apps.mytmdb.presentation.ui.theme.AppTheme


@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                    val currentRoute = currentDestination?.route ?: "home_route"

                    Scaffold(
                        bottomBar = {
                            MovieBottomNavigation(
                                onDestinationChanged = { topLevelDestination ->
                                    navController.navigateToTopLevelDestination(topLevelDestination)
                                },
                                currentRoute = currentRoute,
                                currentDestination = currentDestination
                            )
                        }
                    ) { padding ->
                        MovieNavHost(modifier = Modifier.padding(padding), navController)
                    }
                }
            }
        }
    }
}