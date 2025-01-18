package org.deep.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform