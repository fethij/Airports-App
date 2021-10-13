package com.example.minglesports.domain.repository

import com.example.minglesports.data.remote.dto.FlightDto

interface FlightRepository {
    suspend fun getFlights(): List<FlightDto>
}