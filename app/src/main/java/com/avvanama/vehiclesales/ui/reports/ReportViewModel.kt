package com.avvanama.vehiclesales.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReportViewModel (private val vehicleRepository: VehicleRepository): ViewModel() {

    val vehiclesUiState: StateFlow<VehiclesUiState> =
        vehicleRepository.getAllVehicles().map { vehicles ->
            VehiclesUiState(vehicles)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = VehiclesUiState()
            )
}
data class VehiclesUiState(val vehicleList: List<Vehicle> = listOf())