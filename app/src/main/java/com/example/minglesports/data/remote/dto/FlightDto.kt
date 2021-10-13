package com.example.minglesports.data.remote.dto

import com.example.minglesports.domain.model.Flight

data class FlightDto(
    val airlineId : String,
    val flightNumber : Int,
    val departureAirportId : String,
    val arrivalAirportId : String,
)

fun FlightDto.toFlight(): Flight {
    return Flight(
        airlineId = airlineId,
        flightNumber = flightNumber,
        departureAirportId = departureAirportId,
        arrivalAirportId = arrivalAirportId
    )
}