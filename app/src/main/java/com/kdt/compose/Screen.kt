package com.kdt.compose

sealed class Screen (val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailCar : Screen("home/{carId}") {
        fun createRoute(carId: Int) = "home/$carId"
    }
}