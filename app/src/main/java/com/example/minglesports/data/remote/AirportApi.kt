package com.example.minglesports.data.remote
import com.example.minglesports.data.remote.dto.AirportDto
import com.example.minglesports.data.remote.dto.FlightDto
import retrofit2.http.GET

interface AirportApi {
    @GET("airports.json")
    suspend fun getAirports(): List<AirportDto>

    @GET("flights.json")
    suspend fun getFlights(): List<FlightDto>
}