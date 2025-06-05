package org.qreative.weatherapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.qreative.weatherapp.data.models.WeatherResponse
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import org.qreative.weatherapp.utils.logMessage

class ApiService {

    private val baseUrl = "https://api.openweathermap.org/data/2.5/weather"
    private val appID = "ff11592eb6c4d1ad522fa161d2aabdfc"
    
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

    suspend fun getWeather(city: String): WeatherResponse? {
        val url = "$baseUrl?q=$city&appid=$appID"
        return fetchWeather(url)
    }

    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherResponse? {
        val url = "$baseUrl?lat=$lat&lon=$lon&appid=$appID"
        return fetchWeather(url)
    }

    private suspend fun fetchWeather(url: String): WeatherResponse? {
        logMessage("Request URL: $url")

        return try {
    // Get the raw response as a String
            val responseText: String = httpClient.get(url).body()
            logMessage("Raw API Response: $responseText")
//            val response: WeatherResponse = Json.decodeFromString(responseText)

            val response: WeatherResponse = Json { ignoreUnknownKeys = true }.decodeFromString(responseText)
//            val responseOld = httpClient.get(url).body<WeatherResponse>()
            logMessage("API Response: $response")
            response
        } catch (e: IOException) {
            logMessage("Network error: ${e.message}")
            null
        } catch (e: SerializationException) {
            logMessage("Serialization error: ${e.message}")
            null
        } catch (e: SocketTimeoutException) {
            logMessage("Request timed out: ${e.message}")
            null
        } catch (e: Exception) {
            logMessage("Unexpected error: ${e.message}")
            null
        }
    }

}