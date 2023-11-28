package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditVehicleViewModel (
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val vehicleRepository: VehicleRepository
): ViewModel() {

    private val vehicleId: Int = savedStateHandle.get<Int>(Arguments.VEHICLE_ID)
        ?: throw IllegalArgumentException("Vehicle ID not found in arguments ${savedStateHandle.keys()}")

    private val vehicleType: VehicleType = savedStateHandle.get<VehicleType>(Arguments.VEHICLE_TYPE)
        ?: throw IllegalArgumentException("Vehicle Type not found in arguments")


    var uiState by mutableStateOf(EditVehicleUiState())
        private set

    init {
        viewModelScope.launch {
            vehicleRepository.getVehicleDetails(vehicleId, vehicleType)
                .filterNotNull()
                .map {
                    if (it.vehicleType == VehicleType.CAR) {
                        EditVehicleUiState (
                            vehicleDetails = VehicleDetails(carDetails = (it as Car).toCarDetails()),
                            vehicleType = vehicleType
                        )
                    } else {
                        EditVehicleUiState(
                            vehicleDetails = VehicleDetails(motorcycleDetails = (it as Motorcycle).toMotorcycleDetails()),
                            vehicleType = vehicleType
                        )
                    }
                }.collect {
                    uiState = it
                }
        }
    }

    fun updateUiState(motorcycleDetails: MotorcycleDetails) {
        uiState =
            EditVehicleUiState(
                vehicleDetails = VehicleDetails(motorcycleDetails = motorcycleDetails),
                vehicleType = VehicleType.MOTORCYCLE,
                isEntryValid = validateInput(motorcycleDetails)
            )
    }

    fun updateUiState(carDetails: CarDetails) {
        uiState =
            EditVehicleUiState(
                vehicleDetails = VehicleDetails(carDetails = carDetails),
                vehicleType = VehicleType.CAR,
                isEntryValid = validateInput(carDetails)
            )
    }

    private fun validateInput(motorcycleDetails: MotorcycleDetails = uiState.vehicleDetails.motorcycleDetails): Boolean {
        return with(motorcycleDetails) {
            name.isNotBlank() && color.isNotBlank() && year.isNotBlank()
                    && machine.isNotBlank() && price.isNotBlank()
                    && stocks.isNotBlank() && transmission.isNotBlank()
                    && suspension.isNotBlank()
        }
    }

    private fun validateInput(carDetails: CarDetails = uiState.vehicleDetails.carDetails): Boolean {
        return with(carDetails) {
            name.isNotBlank() && color.isNotBlank() && year.isNotBlank()
                    && machine.isNotBlank() && price.isNotBlank()
                    && stocks.isNotBlank() && passengerCapacity.isNotBlank()
                    && type.isNotBlank()
        }
    }

    suspend fun updateCar() {
        vehicleRepository.updateCar(uiState.vehicleDetails.carDetails.toCar())
    }

    suspend fun updateMotorcycle() {
        vehicleRepository.updateMotorcycle(uiState.vehicleDetails.motorcycleDetails.toMotorcycle())
    }
}

data class EditVehicleUiState(
    val vehicleDetails: VehicleDetails = VehicleDetails(),
    val vehicleType: VehicleType = VehicleType.CAR,
    val isEntryValid: Boolean = false
)