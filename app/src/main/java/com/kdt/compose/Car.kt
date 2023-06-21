package com.kdt.compose

data class Car(
    val id: Int,
    val name: String,
    val factory: String,
    val image: Int,
    val description: String,
    val type: String,
    val price: String,
    val productionperiod: String,
    var isFavorite: Boolean = false
    )
