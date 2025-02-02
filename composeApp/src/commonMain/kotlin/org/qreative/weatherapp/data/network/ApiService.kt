package org.qreative.weatherapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.qreative.weatherapp.data.models.WeatherResponse

class ApiService {

    private val baseUrl = "https://api.openweathermap.org/data/2.5/weather?q="
//    private val baseUrl = "https://api.openweathermap.org/data/2.5/weather?id="
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                coerceInputValues = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getWeather(city: String): WeatherResponse {
        val appID = "ff11592eb6c4d1ad522fa161d2aabdfc"
        val url = "$baseUrl$city&appid=$appID"
        println("Request URL: $url")
        val response = httpClient.get(url).body<WeatherResponse>()
        println("API Response: $response")
        return response
    }


}