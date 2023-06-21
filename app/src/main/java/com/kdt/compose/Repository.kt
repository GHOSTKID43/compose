package com.kdt.compose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class Repository {
    private val dummyCar = mutableListOf<Car>()
    init {
        if (dummyCar.isEmpty()) {
            CarData.dummyCar.forEach {
                dummyCar.add(it)
            }
        }
    }
    fun getCarById(carId: Int): Car {
        return dummyCar.first {
            it.id == carId
        }
    }
    fun getFavoriteCar(): Flow<List<Car>> {
        return flowOf(dummyCar.filter { it.isFavorite })
    }
    fun searchCar(query: String) = flow {
        val data = dummyCar.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }
    fun updateCar(carId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyCar.indexOfFirst { it.id == carId }
        val result = if (index >= 0) {
            val car = dummyCar[index]
            dummyCar[index] = car.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }
    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}