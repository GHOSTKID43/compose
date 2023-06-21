package com.kdt.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kdt.compose.About
import com.kdt.compose.Detail
import com.kdt.compose.Favorite
import com.kdt.compose.Home

@Composable
fun ComposeSub (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
    bottomBar = {
        if (currentRoute != Screen.DetailCar.route) {
            BottomBar(navController)
        }
    },
    modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                Home(
                    navigateToDetail = { carId ->
                        navController.navigate(Screen.DetailCar.createRoute(carId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                Favorite(
                    navigateToDetail = { carId ->
                        navController.navigate(Screen.DetailCar.createRoute(carId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                About()
            }
            composable(
                route = Screen.DetailCar.route,
                arguments = listOf(
                    navArgument("carId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("carId") ?: -1
                Detail(
                    carId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navItems = listOf(
            NavItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Rounded.FavoriteBorder,
                screen = Screen.Favorite
            ),
            NavItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
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
}