package com.example.minglesports.domain.repository

import com.example.minglesports.data.remote.dto.AirportDto

interface AirportRepository {
    suspend fun getAirports(): List<AirportDto>
}