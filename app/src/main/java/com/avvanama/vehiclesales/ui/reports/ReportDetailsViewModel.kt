package com.avvanama.vehiclesales.ui.reports

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Sales
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetails
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetailsUiState
import com.avvanama.vehiclesales.ui.vehicle.toCarDetails
import com.avvanama.vehiclesales.ui.vehicle.toMotorcycleDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReportDetailsViewModel (
    private val savedStateHandle: SavedStateHandle,
    private val vehicleRepository: VehicleRepository
): ViewModel() {

    private val vehicleId: Int = savedStateHandle.get<Int>(Arguments.VEHICLE_ID)
        ?: throw IllegalArgumentException("Vehicle ID not found in arguments ${savedStateHandle.keys()}")

    private val vehicleType: VehicleType = savedStateHandle.get<VehicleType>(Arguments.VEHICLE_TYPE)
        ?: throw IllegalArgumentException("Vehicle Type not found in arguments")

    val salesUiState: StateFlow<SalesUiState> =
        vehicleRepository.getSales(vehicleId).map { sales ->
            SalesUiState(sales)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SalesUiState()
            )

    val uiState: StateFlow<VehicleDetailsUiState> =
        vehicleRepository.getVehicleDetails(vehicleId, vehicleType)
            .filterNotNull()
            .map {
                if (it.vehicleType == VehicleType.CAR) {
                    VehicleDetailsUiState (
                        vehicleDetails = VehicleDetails(carDetails = (it as Car).toCarDetails()),
                        vehicleType = vehicleType
                    )
                } else {
                    VehicleDetailsUiState(
                        vehicleDetails = VehicleDetails(motorcycleDetails = (it as Motorcycle).toMotorcycleDetails()),
                        vehicleType = vehicleType
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = VehicleDetailsUiState()
            )
}
data class SalesUiState(val salesList: List<Sales> = listOf())