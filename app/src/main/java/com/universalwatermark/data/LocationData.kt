package com.universalwatermark.data

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val altitude: Double = 0.0,
    val speed: Float = 0.0f,
    val address: String = "",
    val houseNumber: String = "",
    val street: String = "",
    val subDistrict: String = "",
    val district: String = "",
    val province: String = "",
    val country: String = "",
    val postalCode: String = ""
)
