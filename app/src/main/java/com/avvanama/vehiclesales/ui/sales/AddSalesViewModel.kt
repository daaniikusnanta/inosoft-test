package com.avvanama.vehiclesales.ui.sales

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Sales
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetails
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetailsUiState
import com.avvanama.vehiclesales.ui.vehicle.getVehicleDetailsCommon
import com.avvanama.vehiclesales.ui.vehicle.toCarDetails
import com.avvanama.vehiclesales.ui.vehicle.toMotorcycleDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.TimeZone

class AddSalesViewModel (
    private val savedStateHandle: SavedStateHandle,
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
                Log.d("addSalesViewModel", "$vehicleId, ${it.vehicleType.toString()}")
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

    var salesUiState by mutableStateOf(SalesUiState())
        private set

    fun updateUiState(salesDetails: SalesDetails) {
        val price = uiState.value.getVehicleDetailsCommon ().price.toDoubleOrNull() ?: 0.0
        val newSalesDetails = salesDetails.copy(
            totalPrice = salesDetails.quantity.toIntOrNull()?.times(price) ?: 0.0
        )
        salesUiState =
            SalesUiState(
                salesDetails = newSalesDetails,
                isEntryValid = validateInput(salesDetails)
            )
    }

    private fun validateInput(inputState: SalesDetails = salesUiState.salesDetails): Boolean {
        return with(inputState) {
            val quantityInt = quantity.toIntOrNull() ?: 0
            quantity.isNotBlank() && quantityInt > 0 && quantityInt <= uiState.value.getVehicleDetailsCommon().stocks.toInt()
        }
    }
    suspend fun addSales() {
        val vehicle = uiState.value.getVehicleDetailsCommon()
        val currentTime = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance(TimeZone.getTimeZone("Jakarta")).time)

        val sales = salesUiState.salesDetails.copy(
            vehicleId = vehicle.id,
            vehicleType = vehicle.vehicleType,
            date = currentTime
        ).toSales()
        vehicleRepository.insertSales(sales)

        val newStock = uiState.value.getVehicleDetailsCommon().stocks.toInt() - sales.quantity
        vehicleRepository.updateStocks(sales.vehicleId, newStock, sales.vehicleType)
    }
}

data class SalesUiState(
    val salesDetails: SalesDetails = SalesDetails(),
    val isEntryValid: Boolean = false
)

data class SalesDetails(
    val id: Int = 0,
    val vehicleId: Int = 0,
    val vehicleType: VehicleType = VehicleType.CAR,
    val quantity: String = "",
    val totalPrice: Double = 0.0,
    val date: String = ""
)

fun SalesDetails.toSales(): Sales {
    return Sales(
        id = id,
        vehicleId = vehicleId,
        vehicleType = vehicleType,
        quantity = quantity.toIntOrNull() ?: 0,
        totalPrice = totalPrice,
        date = date
    )
}

fun Sales.toSalesUiState(isEntryValid: Boolean = false): SalesUiState = SalesUiState(
    salesDetails = this.toSalesDetails(),
    isEntryValid = isEntryValid
)

fun Sales.toSalesDetails(): SalesDetails = SalesDetails(
    id = id,
    vehicleId = vehicleId,
    vehicleType = vehicleType,
    quantity = quantity.toString(),
    totalPrice = totalPrice,
    date = date
)