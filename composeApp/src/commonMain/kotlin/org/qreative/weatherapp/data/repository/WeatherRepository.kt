package org.qreative.weatherapp.data.repository
import org.qreative.weatherapp.data.network.ApiService
import org.qreative.weatherapp.data.models.WeatherResponse

class WeatherRepository {
    private val apiService = ApiService()

    suspend fun fetchWeather(city: String): WeatherResponse? {
        return apiService.getWeather(city)
    }
}