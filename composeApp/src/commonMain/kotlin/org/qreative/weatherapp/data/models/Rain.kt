package org.qreative.weatherapp.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Rain(
    val `1h`: Double?
)