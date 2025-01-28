package org.qreative.weatherapp

//actual class LocationProvider {
//    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
//    }
//
//
//}

import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.CLLocationCoordinate2D

actual class LocationProvider : CLLocationManagerDelegateProtocol {

    private val locationManager = CLLocationManager()

    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
        locationManager.setDelegate(this)
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.firstOrNull() as? CLLocation
        if (location != null) {
            val coordinate = location.coordinate
            callback(coordinate.latitude, coordinate.longitude)
        }
    }
}