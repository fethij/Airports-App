package com.example.minglesports.presentation.airportMapList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minglesports.common.Resource
import com.example.minglesports.domain.model.Airport
import com.example.minglesports.domain.use_case.get_airports.GetAirportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AirportMapListViewModel @Inject constructor(
    private val getAirportUseCase: GetAirportUseCase
): ViewModel() {

    private val _state = mutableStateOf(AirportMapListState())
    val state: State<AirportMapListState> = _state

    init {
        getAirports()
    }

    private fun getAirports(){
        getAirportUseCase().onEach { result ->
            when(result){
                is Resource.Success ->{
                    _state.value = AirportMapListState(airports = result.data ?: emptyList())
                }
                is Resource.Error ->{
                    _state.value = AirportMapListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading ->{
                     _state.value = AirportMapListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectedAirport(latitude:Double,longitude :Double): Airport? {
        //TODO waar wordt onderstaande getAirportsList() gebruikt?

        //getAirportsList()
        val airports = state.value.airports
        for (airport in airports)
        {
            if (latitude==airport.latitude && longitude==airport.longitude)
            {

                return airport
            }

        }
        return null
    }

}