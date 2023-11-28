package com.avvanama.vehiclesales.ui.vehicle

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.components.BackButtonTopBar
import kotlinx.coroutines.launch

@Composable
fun VehicleDetails(
    onBackClick: () -> Unit,
    navigateToEditVehicle: (Int, VehicleType) -> Unit,
    viewModel: VehicleDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = { BackButtonTopBar (
            title = "Vehicle Details",
            onBackClick = onBackClick
        ) }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
            Column (modifier = Modifier.padding(16.dp)) {
                VehicleDetailsBody(uiState = uiState.value)
                Spacer(modifier = Modifier.padding(16.dp))
                VehicleDetailsButtons(
                    onDeleteButtonClicked = { coroutineScope.launch {
                        viewModel.deleteVehicle()
                        onBackClick()
                    } },
                    onEditButtonClicked = {
                        if (uiState.value.vehicleType == VehicleType.CAR) {
                            navigateToEditVehicle(uiState.value.vehicleDetails.carDetails.id, VehicleType.CAR)
                        } else {
                            navigateToEditVehicle(uiState.value.vehicleDetails.carDetails.id, VehicleType.CAR)
                        }},
                    onAddStocksButtonClicked = { /*TODO*/ },
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun VehicleDetailsButtons(
    onDeleteButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onAddStocksButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Column (modifier = modifier) {
        Row (modifier = modifier) {
            Button(onClick = onDeleteButtonClicked, modifier = Modifier.weight(1f)) {
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = onEditButtonClicked, modifier = Modifier.weight(1f)) {
                Text(text = "Edit")
            }

        }
//        Spacer(modifier = Modifier.padding(8.dp))
//        Button(onClick = onAddStocksButtonClicked, modifier = Modifier.weight(1f)) {
//            Text(text = "Add Stocks")
//        }
    }
}

@Composable
fun VehicleDetailsBody(
    uiState: VehicleDetailsUiState
) {
    Log.d("VehicleDetails", "VehicleDetailsBody: ${uiState.vehicleDetails}")
    Column {
        if (uiState.vehicleType == VehicleType.CAR) {
            val vehicle = uiState.vehicleDetails.carDetails
            DetailField(label = "Name", value = vehicle.name)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Price", value = vehicle.price)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Year", value = vehicle.year)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Color", value = vehicle.color)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Machine", value = vehicle.machine)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Passenger Capacity", value = vehicle.passengerCapacity)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Type", value = vehicle.type)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Current Stock", value = vehicle.stocks)
        } else {
            val vehicle = uiState.vehicleDetails.motorcycleDetails
            DetailField(label = "Name", value = vehicle.name)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Price", value = vehicle.price)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Year", value = vehicle.year)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Color", value = vehicle.color)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Machine", value = vehicle.machine)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Suspension", value = vehicle.suspension)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Transmission", value = vehicle.transmission)
            Spacer(modifier = Modifier.padding(8.dp))
            DetailField(label = "Current Stock", value = vehicle.stocks)
        }
    }
}

@Composable
fun DetailField(
    label: String,
    value: String
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}