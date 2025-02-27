package org.qreative.weatherapp.utils
import android.util.Log

actual fun logMessage(message: String) {
    Log.d("CustomLog", message)
}