package com.example.minglesports.presentation.airportsFromSchiphol

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minglesports.common.Resource
import com.example.minglesports.domain.model.Airport
import com.example.minglesports.domain.model.FlightsDistanceModel
import com.example.minglesports.domain.use_case.get_flights.GetFlightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@HiltViewModel
class AirportsFromSchipholViewModel @Inject constructor(
    private val getFlightUseCase: GetFlightUseCase
) : ViewModel() {
    private val _state = mutableStateOf(FlightsState())
    private val state: State<FlightsState> = _state

    init {
        getFlights()
    }

    private fun getFlights() {
        getFlightUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FlightsState(flights = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = FlightsState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = FlightsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSortedList(airportsList: List<Airport>?): List<FlightsDistanceModel> {
       // getFlights()
        val distances: ArrayList<FlightsDistanceModel> = ArrayList()

        //Schiphol Airport Coordinates
        val lat1 = 52.30907
        val long2 = 4.763385
        for (flight in state.value.flights) {

            for (airport in airportsList!!) {
//TODO "AMS" in een variable zetten
                if (flight.departureAirportId == "AMS" && flight.arrivalAirportId == airport.id
                ) {
                    if (distance(
                            lat1,
                            long2,
                            airport.latitude,
                            airport.longitude
                        ) < 80000.0
                    ) {
                        val model = FlightsDistanceModel(
                            airport = airport,
                            distance = distance(
                                lat1,
                                long2,
                                airport.latitude,
                                airport.longitude
                            )
                        )
                        distances.add(model)
                    }
                }
            }
        }
        return distances.sortedWith(compareBy { it.distance })


      //  return emptyList()
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist = (dist * 100.0).roundToInt() / 100.0
        return dist;
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
}