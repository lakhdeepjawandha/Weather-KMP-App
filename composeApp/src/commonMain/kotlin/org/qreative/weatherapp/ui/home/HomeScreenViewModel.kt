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
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = HomeScreenState.Loading
            try {
                val response = repository.fetchWeather(city)
                _state.value = HomeScreenState.Success(response)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    fun kelvinToFahrenheit(kelvin: Double): Double {
        return (kelvin - 273.15) * 9/5 + 32
    }
}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val weather: WeatherResponse) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}