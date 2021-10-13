package com.example.minglesports.data.remote.dto

import com.example.minglesports.domain.model.Airport

data class AirportDto(
    val city: String,
    val countryId: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)

fun AirportDto.toAirport(): Airport{
    return Airport(
        city = city,
        countryId = countryId,
        id = id,
        latitude = latitude,
        longitude = longitude,
        name =name
    )
}
