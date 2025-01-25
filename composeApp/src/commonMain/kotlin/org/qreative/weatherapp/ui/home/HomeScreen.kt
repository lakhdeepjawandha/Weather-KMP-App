package org.qreative.weatherapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.qreative.weatherapp.data.models.WeatherResponse

@Composable
fun HomeScreen() {
    val viewModel = remember { HomeScreenViewModel() }
    LaunchedEffect(Unit) {
        viewModel.fetchWeather("london")
    }

    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        when (state.value) {
            is HomeScrenState.Loading -> {
                CircularProgressIndicator()
                Text(text = "Loading")
                // Loading
            }

            is HomeScrenState.Success -> {
                val weather = (state.value as HomeScrenState.Success).weather
                HomeScreenContent(weather)
            }

            is HomeScrenState.Error -> {
                val message = (state.value as HomeScrenState.Error).message
                Text(text = message)
            }
        }
    }
}

@Composable
fun HomeScreenContent(weather: WeatherResponse) {
    Column {
        Text(text = weather.name)
        Text(text = weather.weather[0].description)
    }
}