package com.example.minglesports.data.repository

import com.example.minglesports.data.remote.AirportFlightApi
import com.example.minglesports.data.remote.dto.AirportDto
import com.example.minglesports.domain.repository.AirportRepository
import javax.inject.Inject

class AirportRepositoryImpl @Inject constructor(
    private val flightApi: AirportFlightApi
) : AirportRepository{
    override suspend fun getAirports(): List<AirportDto> {
        return flightApi.getAirports()
    }
}