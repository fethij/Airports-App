package com.example.minglesports.data.repository

import com.example.minglesports.data.remote.AirportApi
import com.example.minglesports.data.remote.dto.FlightDto
import com.example.minglesports.domain.repository.FlightRepository
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val api: AirportApi
) : FlightRepository{
    override suspend fun getFlights(): List<FlightDto> {
        return api.getFlights()
    }

}