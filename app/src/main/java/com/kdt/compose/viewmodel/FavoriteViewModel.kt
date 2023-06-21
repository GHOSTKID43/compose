package com.kdt.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdt.compose.Car
import com.kdt.compose.Repository
import com.kdt.compose.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _stateUi: MutableStateFlow<StateUi<List<Car>>> = MutableStateFlow(StateUi.Loading)
    val stateUi: StateFlow<StateUi<List<Car>>>
        get() = _stateUi

    fun getFavoriteCar() = viewModelScope.launch {
        repository.getFavoriteCar()
            .catch {
                _stateUi.value = StateUi.Error(it.message.toString())
            }
            .collect {
                _stateUi.value = StateUi.Success(it)
            }
    }

    fun updateCar(id: Int, newState: Boolean) {
        repository.updateCar(id, newState)
        getFavoriteCar()
    }
}