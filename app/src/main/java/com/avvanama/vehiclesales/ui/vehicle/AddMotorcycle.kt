package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.foundation.background
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.components.AddVehicleTextField
import com.avvanama.vehiclesales.ui.components.BackButtonTopBar
import kotlinx.coroutines.launch

@Composable
fun AddMotorcycle(
    onBackClick: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddMotorcycleViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val motorcycleUiState = viewModel.motorcycleUiState
    val motorcycleDetails = motorcycleUiState.motorcycleDetails
    val scrollState = rememberScrollState()

    Scaffold (
        topBar = { BackButtonTopBar("Add Motorcycle", navigateBack) }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
//        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .verticalScroll(scrollState)
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
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.addMotorcycle()
                        onBackClick()
                    }
                },
                enabled = motorcycleUiState.isEntryValid,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add Motorcycle")
            }
        }
    }
}