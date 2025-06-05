package org.qreative.weatherapp

expect class LocationProvider() {
    fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit)
}