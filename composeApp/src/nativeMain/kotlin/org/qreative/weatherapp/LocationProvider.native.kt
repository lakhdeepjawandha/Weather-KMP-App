package org.qreative.weatherapp

import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied

import platform.CoreLocation.CLLocationCoordinate2D

actual class LocationProvider : NSObject(), CLLocationManagerDelegateProtocol {

    private val locationManager = CLLocationManager()
    private var callback: ((lat: Double, lon: Double) -> Unit)? = null

    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
        this.callback = callback
        locationManager.delegate = this
        locationManager.setDelegate(this)
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.firstOrNull() as? CLLocation
        location?.let {
            callback?.invoke(it.coordinate.latitude, it.coordinate.longitude)
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        println("Failed to get location: ${didFailWithError.localizedDescription}")
    }

    // Required implementation of `NSObjectProtocol`
    override fun `class`(): ObjCClass? {
        return null
    }
}