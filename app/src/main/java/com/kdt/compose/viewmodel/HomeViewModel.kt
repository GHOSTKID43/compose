package com.kdt.compose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdt.compose.Car
import com.kdt.compose.Repository
import com.kdt.compose.StateUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _stateUi: MutableStateFlow<StateUi<List<Car>>> =
        MutableStateFlow(StateUi.Loading)
    val stateUi: StateFlow<StateUi<List<Car>>>
        get() = _stateUi

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchCar(_query.value)
            .catch {
                _stateUi.value = StateUi.Error(it.message.toString())
            }
            .collect {
                _stateUi.value = StateUi.Success(it)
            }
    }

    fun updateCar(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateCar(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}