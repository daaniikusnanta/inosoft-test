package com.avvanama.vehiclesales.ui.vehicle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class VehicleDetailsViewModel (
    savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val vehicleRepository: VehicleRepository
): ViewModel() {

    private val vehicleId: Int = savedStateHandle.get<Int>(Arguments.VEHICLE_ID)
        ?: throw IllegalArgumentException("Vehicle ID not found in arguments ${savedStateHandle.keys()}")

    private val vehicleType: VehicleType = savedStateHandle.get<VehicleType>(Arguments.VEHICLE_TYPE)
        ?: throw IllegalArgumentException("Vehicle Type not found in arguments")


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

    suspend fun deleteVehicle() {
        vehicleRepository.deleteVehicle(vehicleId)
    }
}

data class VehicleDetailsUiState(
    val vehicleDetails: VehicleDetails = VehicleDetails(),
    val vehicleType: VehicleType = VehicleType.CAR
)

data class VehicleDetails(
    val carDetails: CarDetails = CarDetails(),
    val motorcycleDetails: MotorcycleDetails = MotorcycleDetails(),
)

data class VehicleDetailsCommon(
    val id: Int = 0,
    val name: String = "",
    val year: String = "",
    val color: String = "",
    val price: String = "",
    val stocks: String = "",
    val vehicleType: VehicleType = VehicleType.CAR,
)

fun CarDetails.toVehicleDetailsCommon(): VehicleDetailsCommon {
    return VehicleDetailsCommon(
        id = id,
        name = name,
        year = year.toString(),
        color = color,
        price = price.toString(),
        stocks = stocks.toString(),
        vehicleType = vehicleType
    )
}

fun MotorcycleDetails.toVehicleDetailsCommon(): VehicleDetailsCommon {
    return VehicleDetailsCommon(
        id = id,
        name = name,
        year = year.toString(),
        color = color,
        price = price.toString(),
        stocks = stocks.toString(),
        vehicleType = vehicleType
    )
}

fun VehicleDetailsUiState.getVehicleDetailsCommon(): VehicleDetailsCommon {
    return if (vehicleType == VehicleType.CAR) {
        vehicleDetails.carDetails.toVehicleDetailsCommon()
    } else {
        vehicleDetails.motorcycleDetails.toVehicleDetailsCommon()
    }
}