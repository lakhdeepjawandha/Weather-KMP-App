package org.qreative.weatherapp.data.repository
import org.qreative.weatherapp.data.network.ApiService

class WeatherRepository {
    private val apiservice = ApiService()

    suspend fun fetchWeather(city: String) = apiservice.getWeather(city)
}