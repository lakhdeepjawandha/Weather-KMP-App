package org.qreative.weatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//actual class LocationProvider {
//    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
//    }
//}

actual class LocationProvider(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    actual fun getCurrentLocation(callback: (lat: Double, lon: Double) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, fetch location
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    callback(location.latitude, location.longitude)
                } else {
                    // Handle null location (e.g., location services disabled)
                    println("Location is null. Ensure location services are enabled.")
                }
            }
        } else {
            // Permission not granted, request it
            requestLocationPermission(callback)
        }
    }

    private fun requestLocationPermission(callback: (lat: Double, lon: Double) -> Unit) {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION_PERMISSION
            )
        } else {
            // Handle non-Activity context
            println("Context is not an Activity. Cannot request permissions.")
        }
    }

    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1001
    }
}