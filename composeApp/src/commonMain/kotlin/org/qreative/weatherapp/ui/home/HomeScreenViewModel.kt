package org.qreative.weatherapp.ui.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.qreative.weatherapp.data.models.WeatherResponse
import org.qreative.weatherapp.data.repository.WeatherRepository

class HomeScreenViewModel {

    val repository = WeatherRepository()
    private val _state = MutableStateFlow<HomeScrenState>(HomeScrenState.Loading)
    val state = _state.asStateFlow()

    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = HomeScrenState.Loading
            try {
                val response = repository.fetchWeather(city)
                _state.value = HomeScrenState.Success(response)
            } catch (e: Exception) {
                println(e.message)
                _state.value = HomeScrenState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}

sealed class HomeScrenState {
    object Loading : HomeScrenState()
    data class Success(val weather: WeatherResponse) : HomeScrenState()
    data class Error(val message: String) : HomeScrenState()
}