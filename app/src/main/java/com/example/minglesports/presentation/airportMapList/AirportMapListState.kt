package com.example.minglesports.presentation.airportMapList

import com.example.minglesports.domain.model.Airport

data class AirportMapListState(
    val isLoading: Boolean = false,
    // uitleg emptyList()
    val airports: List<Airport> = emptyList(),
    val error: String = ""
)
