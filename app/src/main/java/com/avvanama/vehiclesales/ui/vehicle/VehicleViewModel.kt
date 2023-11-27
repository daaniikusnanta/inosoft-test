package com.avvanama.vehiclesales.ui.vehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleDatabase
import com.avvanama.vehiclesales.database.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(private val vehicleRepository: VehicleRepository): ViewModel() {

    val vehicles: StateFlow<List<Vehicle>> =
        vehicleRepository.getAllVehicles().map { vehicles ->
            vehicles
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}