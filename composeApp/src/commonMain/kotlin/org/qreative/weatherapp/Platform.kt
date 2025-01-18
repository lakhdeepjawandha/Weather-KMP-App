package org.qreative.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform