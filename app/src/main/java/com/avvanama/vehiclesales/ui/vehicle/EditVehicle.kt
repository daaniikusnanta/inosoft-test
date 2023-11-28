package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.components.AddVehicleTextField
import com.avvanama.vehiclesales.ui.components.BackButtonTopBar
import kotlinx.coroutines.launch

@Composable
fun EditVehicle(
    onBackClick: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: EditVehicleViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    Scaffold (
        topBar = { BackButtonTopBar("Edit Vehicles", navigateBack) }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
//        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            if (uiState.vehicleType == VehicleType.CAR) {
                CarForm(uiState.vehicleDetails.carDetails, viewModel,
                    Modifier.padding(16.dp)
                    .weight(1f)
                    .verticalScroll(scrollState))
            } else {
                MotorcycleForm(uiState.vehicleDetails.motorcycleDetails, viewModel,
                    Modifier.padding(16.dp)
                    .weight(1f)
                    .verticalScroll(scrollState))
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (uiState.vehicleType == VehicleType.CAR) {
                            viewModel.updateCar()
                        } else {
                            viewModel.updateMotorcycle()
                        }
                        onBackClick()
                    }
                },
                enabled = uiState.isEntryValid,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Edit Vehicle")
            }
        }
    }
}

@Composable
fun CarForm (
    carDetails: CarDetails,
    viewModel: EditVehicleViewModel,
    modifier: Modifier
) {
    Column (
        modifier = modifier
    ) {
        AddVehicleTextField(label = "Name", value = carDetails.name, onValueChange = { viewModel.updateUiState(carDetails.copy(name = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Year", value = carDetails.year, onValueChange = { viewModel.updateUiState(carDetails.copy(year = it)) }, keyboardType = KeyboardType.Number)

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Color", value = carDetails.color, onValueChange = { viewModel.updateUiState(carDetails.copy(color = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Machine", value = carDetails.machine, onValueChange = { viewModel.updateUiState(carDetails.copy(machine = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Passenger Capacity", value = carDetails.passengerCapacity, onValueChange = { viewModel.updateUiState(carDetails.copy(passengerCapacity = it)) }, keyboardType = KeyboardType.Number)

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Type", value = carDetails.type, onValueChange = { viewModel.updateUiState(carDetails.copy(type = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Price", value = carDetails.price, onValueChange = { viewModel.updateUiState(carDetails.copy(price = it)) }, keyboardType = KeyboardType.Decimal)

        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Current Stocks", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.padding(4.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            TextField(
                value = carDetails.stocks,
                onValueChange = { viewModel.updateUiState(carDetails.copy(stocks = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.wrapContentWidth()
            )
            Text(
                text = "units",
                modifier = Modifier
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MotorcycleForm (
    motorcycleDetails: MotorcycleDetails,
    viewModel: EditVehicleViewModel,
    modifier: Modifier
) {
    Column (
        modifier = modifier
    ) {
        AddVehicleTextField(label = "Name", value = motorcycleDetails.name, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(name = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Year", value = motorcycleDetails.year, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(year = it)) }, keyboardType = KeyboardType.Number)

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Color", value = motorcycleDetails.color, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(color = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Machine", value = motorcycleDetails.machine, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(machine = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Suspension", value = motorcycleDetails.suspension, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(suspension = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Transmission", value = motorcycleDetails.transmission, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(transmission = it)) })

        Spacer(modifier = Modifier.padding(16.dp))
        AddVehicleTextField(label = "Price", value = motorcycleDetails.price, onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(price = it)) }, keyboardType = KeyboardType.Decimal)

        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Current Stocks", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.padding(4.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            TextField(
                value = motorcycleDetails.stocks,
                onValueChange = { viewModel.updateUiState(motorcycleDetails.copy(stocks = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.wrapContentWidth()
            )
            Text(
                text = "units",
                modifier = Modifier
                    .padding(start = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}