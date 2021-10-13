package com.example.minglesports.presentation.airportMapList.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.minglesports.domain.model.Airport
import com.example.minglesports.domain.use_case.get_airports.GetAirportUseCase
import com.example.minglesports.presentation.airportMapList.AirportMapListViewModel
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

@Composable
fun AirportTab(
    context: Context,
    viewModel: AirportMapListViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        ShowAirports(viewModel = viewModel, context = context)

    }
}

@Composable
fun ShowAirports(
    viewModel: AirportMapListViewModel = hiltViewModel(),
    context: Context
) {
    viewModel.state.value.airports
    AirportsInMap(
        context,
        viewModel.state.value.airports,
        Modifier.fillMaxSize()
    )
}

@Composable
fun AirportsInMap(context: Context, airportList: List<Airport>, modifier: Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {

        MapView(airportList, context)
    }

}


@Composable
private fun MapView(airportList: List<Airport>?, context: Context) {
    // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
    // with input from Compose UI, those updates are encapsulated into the MapViewContainer
    // composable. In this way, when an update to the MapView happens, this composable won't
    // recompose and the MapView won't need to be recreated.
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(mapView, airportList, context)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    airportList: List<Airport>?, context: Context
) {


    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            // Zoom Controls in map
            googleMap.uiSettings.isZoomControlsEnabled = true

            if (airportList != null) {
                for (airport in airportList) {

                    googleMap.addMarker {

                        /*MarkerOptions().position(LatLng(airport.latitude, airport.longitude))
                        .title(airport.name)
                        .snippet(airport.city)
                        .draggable(true)*/
                        position(LatLng(airport.latitude, airport.longitude))
                    }
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                airport.latitude,
                                airport.longitude
                            )
                        )
                    )
                    googleMap.setOnMarkerClickListener { marker ->
                        if (marker.isInfoWindowShown) {
                            marker.hideInfoWindow()

                        } else {
                            marker.showInfoWindow()
                        }
//                        val intent = Intent(context, AirPortDetail::class.java)
//                        intent.putExtra("lat", marker.position.latitude)
//                        intent.putExtra("long", marker.position.longitude)
//                        context.startActivity(intent)
                        true
                    }
                }

            }
        }
    }
}
