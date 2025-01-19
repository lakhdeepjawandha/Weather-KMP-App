package org.qreative.weatherapp

actual class UrlProvider {
    actual fun getUrl(): String {
        return "https://www.google.com"
    }
}