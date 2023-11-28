package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AddMotorcycleViewModel (private val vehicleRepository: VehicleRepository): ViewModel() {

    var motorcycleUiState by mutableStateOf(MotorcycleUiState())
        private set

    fun updateUiState(motorcycleDetails: MotorcycleDetails) {
        motorcycleUiState =
            MotorcycleUiState(motorcycleDetails = motorcycleDetails, isEntryValid = validateInput(motorcycleDetails))
    }

    private fun validateInput(uiState: MotorcycleDetails = motorcycleUiState.motorcycleDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && color.isNotBlank() && year.isNotBlank()
                    && machine.isNotBlank() && price.isNotBlank()
                    && stocks.isNotBlank() && transmission.isNotBlank()
                    && suspension.isNotBlank()
        }
    }
    suspend fun addMotorcycle() {
        vehicleRepository.insertMotorcycle(motorcycleUiState.motorcycleDetails.toMotorcycle())
    }
}

data class MotorcycleUiState(
    val motorcycleDetails: MotorcycleDetails = MotorcycleDetails(),
    val isEntryValid: Boolean = false
)

data class MotorcycleDetails(
    val id: Int = 0,
    val name: String = "",
    val year: String = "",
    val color: String = "",
    val price: String = "",
    val stocks: String = "",
    val vehicleType: VehicleType = VehicleType.MOTORCYCLE,
    val machine: String = "",
    val suspension: String = "",
    val transmission: String = ""
)

fun MotorcycleDetails.toMotorcycle(): Motorcycle {
    return Motorcycle(
        machine = machine,
        suspension = suspension,
        transmission = transmission
    ).apply {
        id = this@toMotorcycle.id
        name = this@toMotorcycle.name
        year = this@toMotorcycle.year.toIntOrNull() ?: 0
        color = this@toMotorcycle.color
        price = this@toMotorcycle.price.toDoubleOrNull() ?: 0.0
        stocks = this@toMotorcycle.stocks.toIntOrNull() ?: 0
        vehicleType = this@toMotorcycle.vehicleType
    }
}

fun Motorcycle.toMotorcycleUiState(isEntryValid: Boolean = false): MotorcycleUiState = MotorcycleUiState(
    motorcycleDetails = this.toMotorcycleDetails(),
    isEntryValid = isEntryValid
)

fun Motorcycle.toMotorcycleDetails(): MotorcycleDetails = MotorcycleDetails(
    id = id,
    name = name,
    year = year.toString(),
    color = color,
    price = price.toString(),
    stocks = stocks.toString(),
    vehicleType = vehicleType,
    machine = machine,
    suspension = suspension,
    transmission = transmission
)