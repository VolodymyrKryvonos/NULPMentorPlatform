package com.nulp.mentor.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nulp.mentor.common.PrefService
import com.nulp.mentor.presentation.common.BottomNavItem
import com.nulp.mentor.presentation.common.BottomNavigationBar
import com.nulp.mentor.presentation.home_screen.HomeScreen
import com.nulp.mentor.presentation.theme.NULPMentorPlatformTheme
import com.nulp.mentor.presentation.login_screen.SignInScreen
import com.nulp.mentor.presentation.main_screen.ChatScreen
import com.nulp.mentor.presentation.main_screen.SettingsScreen
import com.nulp.mentor.presentation.register_screen.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefService: PrefService

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NULPMentorPlatformTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                Scaffold(bottomBar = {
                    if (backStackEntry.value?.destination?.route == Screen.HomeScreen.route ||
                        backStackEntry.value?.destination?.route == Screen.NotificationScreen.route ||
                        backStackEntry.value?.destination?.route == Screen.AccountScreen.route
                    ) {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Головна",
                                    route = Screen.HomeScreen.route,
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Сповіщення",
                                    route = Screen.NotificationScreen.route,
                                    icon = Icons.Default.Notifications
                                ),
                                BottomNavItem(
                                    name = "Акаунт",
                                    route = Screen.AccountScreen.route,
                                    icon = Icons.Default.AccountCircle
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    if (navController.backQueue.size > 2) {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        )
                    }
                }) {
                    NavHost(
                        navController = navController,
                        startDestination = if (prefService.isLoggedIn()) {
                            Screen.HomeScreen.route
                        } else {
                            Screen.RegisterScreenNavigation.route
                        }
                    ) {
                        composable(route = Screen.LoginScreenNavigation.route) {
                            SignInScreen(navController)
                        }
                        composable(route = Screen.RegisterScreenNavigation.route) {
                            SignUpScreen(navController)
                        }
                        composable(route = Screen.HomeScreen.route) {
                            Log.e("HomeScreen: ", route?:"432")
                            HomeScreen(navController)
                        }
                        composable(route = Screen.NotificationScreen.route) {
                            ChatScreen()
                        }
                        composable(route = Screen.AccountScreen.route) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}

