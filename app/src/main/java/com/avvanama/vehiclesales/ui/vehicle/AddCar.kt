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


//@Preview(showBackground = true)
@Composable
fun AddCar(
    onBackClick: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddCarViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val carUiState = viewModel.carUiState
    val carDetails = carUiState.carDetails
    val scrollState = rememberScrollState()

    Scaffold (
      topBar = { BackButtonTopBar("Add Car", navigateBack) }
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
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.addCar()
                        onBackClick()
                    }
                },
                enabled = carUiState.isEntryValid,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add Car")
            }
        }
    }
}