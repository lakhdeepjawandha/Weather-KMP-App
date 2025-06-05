package org.qreative.weatherapp.ui.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.qreative.weatherapp.data.models.WeatherResponse
import org.qreative.weatherapp.data.repository.WeatherRepository
import org.qreative.weatherapp.LocationProvider

class HomeScreenViewModel {

    val repository = WeatherRepository()
    private val locationProvider = LocationProvider()
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = HomeScreenState.Loading
            try {
                val response = repository.fetchWeather(city)
                if (response != null) {
                    _state.value = HomeScreenState.Success(response)
                } else {
                    _state.value = HomeScreenState.Error("Failed to fetch weather data")
                }
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error(e.message ?: "An unexpected error occurred")
                println(e.message)
            }
        }
    }

    fun fetchWeatherByLocation() {
        _state.value = HomeScreenState.Loading
        locationProvider.getCurrentLocation { lat, lon ->
            println("Location received: lat=$lat, lon=$lon")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = repository.fetchWeatherByCoordinates(lat, lon)
                    if (response != null) {
                        _state.value = HomeScreenState.Success(response)
                    } else {
                        _state.value = HomeScreenState.Error("Failed to fetch weather data")
                    }
                } catch (e: Exception) {
                    _state.value = HomeScreenState.Error(e.message ?: "An unexpected error occurred")
                    println(e.message)
                }
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