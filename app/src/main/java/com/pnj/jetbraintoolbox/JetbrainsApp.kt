package com.pnj.jetbraintoolbox

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pnj.jetbraintoolbox.ui.navigation.NavigationItem
import com.pnj.jetbraintoolbox.ui.navigation.Screen
import com.pnj.jetbraintoolbox.ui.screen.detail.DetailScreen
import com.pnj.jetbraintoolbox.ui.screen.home.HomeScreen
import com.pnj.jetbraintoolbox.ui.screen.profile.ProfileScreen
import com.pnj.jetbraintoolbox.ui.theme.JetbrainToolboxTheme
import java.util.Locale

@Composable
fun JetBrainsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = {
                        navController.navigate(Screen.Detail.createRoute(it))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) {
                val name = it.arguments?.getString("name") ?: ""
                DetailScreen(name) {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(modifier) {
        val navigationItems = listOf(
            NavigationItem(
                "Home",
                Icons.Default.Home,
                Screen.Home
            ),
            NavigationItem(
                "About",
                Icons.Default.AccountCircle,
                Screen.Profile
            )
        )

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.title.lowercase(Locale.ROOT)}_page"
                    )
                },
                alwaysShowLabel = false,
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewApps() {
    JetbrainToolboxTheme {
        JetBrainsApp()
    }
}

