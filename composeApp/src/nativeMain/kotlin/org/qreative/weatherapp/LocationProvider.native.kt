package org.qreative.weatherapp

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual class LocationProvider {
    private val locationManager = CLLocationManager()
    private var callback: ((lat: Double, lon: Double) -> Unit)? = null
    private var locationDelegate: LocationDelegate? = null

    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
        this.callback = callback
        locationDelegate = LocationDelegate(locationManager, callback)
        locationManager.delegate = locationDelegate

        val status = CLLocationManager.authorizationStatus()
        if (status == kCLAuthorizationStatusAuthorizedWhenInUse ||
            status == kCLAuthorizationStatusAuthorizedAlways) {
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.startUpdatingLocation()
        } else {
            locationManager.requestWhenInUseAuthorization()
        }
    }

//    actual fun dispose() {
//        locationManager.delegate = null
//        locationDelegate = null
//        locationManager.stopUpdatingLocation()
//    }

    private class LocationDelegate(private val locationManager: CLLocationManager,
        private val callback: (lat: Double, lon: Double) -> Unit
    ) : NSObject(), CLLocationManagerDelegateProtocol {

        @Suppress("UNCHECKED_CAST")
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            val locations = didUpdateLocations as? List<CLLocation> ?: return
            locations.firstOrNull()?.let { location ->

                location.coordinate.useContents {
                    callback?.invoke(
                        this.latitude,
                        this.longitude
                    )
                }
                
                locationManager.stopUpdatingLocation()
            } ?: println("No valid location data received")
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            println("Failed to get location: ${didFailWithError.localizedDescription}")
            callback?.invoke(0.0, 0.0) // Default values or error indicator
            manager.stopUpdatingLocation()
        }
    }
}