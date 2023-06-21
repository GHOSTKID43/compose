package com.kdt.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdt.compose.Car
import com.kdt.compose.Repository
import com.kdt.compose.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _stateUi: MutableStateFlow<StateUi<Car>> =
        MutableStateFlow(StateUi.Loading)
    val stateUi: StateFlow<StateUi<Car>>
        get() = _stateUi

    fun getCarById(id: Int) = viewModelScope.launch {
        _stateUi.value = StateUi.Loading
        _stateUi.value = StateUi.Success(repository.getCarById(id))
    }


    fun updateCar(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateCar(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getCarById(id)
            }
    }

}