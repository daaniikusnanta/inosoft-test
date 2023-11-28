package com.avvanama.vehiclesales.ui.vehicle

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AddCarViewModel (private val vehicleRepository: VehicleRepository): ViewModel() {

    var carUiState by mutableStateOf(CarUiState())
        private set

    fun updateUiState(carDetails: CarDetails) {
        carUiState =
            CarUiState(carDetails = carDetails, isEntryValid = validateInput(carDetails))
    }

    private fun validateInput(uiState: CarDetails = carUiState.carDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && stocks.isNotBlank()
        }
    }
    suspend fun addCar() {
        vehicleRepository.insertCar(carUiState.carDetails.toCar())
    }
}

data class CarUiState(
    val carDetails: CarDetails = CarDetails(),
    val isEntryValid: Boolean = false
)

data class CarDetails(
    val id: Int = 0,
    val name: String = "",
    val year: String = "",
    val color: String = "",
    val price: String = "",
    val stocks: String = "",
    val vehicleType: VehicleType = VehicleType.CAR,
    val machine: String = "",
    val passengerCapacity: String = "",
    val type: String = ""
)

fun CarDetails.toCar(): Car {
    return Car(
        machine = machine,
        passengerCapacity = passengerCapacity.toIntOrNull() ?: 0,
        type = type
    ).apply {
        id = this@toCar.id
        name = this@toCar.name
        year = this@toCar.year.toIntOrNull() ?: 0
        color = this@toCar.color
        price = this@toCar.price.toDoubleOrNull() ?: 0.0
        stocks = this@toCar.stocks.toIntOrNull() ?: 0
        vehicleType = this@toCar.vehicleType
    }
}

fun Car.toCarUiState(isEntryValid: Boolean = false): CarUiState = CarUiState(
    carDetails = this.toCarDetails(),
    isEntryValid = isEntryValid
)

fun Car.toCarDetails(): CarDetails = CarDetails(
    id = id,
    name = name,
    year = year.toString(),
    color = color,
    price = price.toString(),
    stocks = stocks.toString(),
    vehicleType = vehicleType,
    machine = machine,
    passengerCapacity = passengerCapacity.toString(),
    type = type
)