package com.avvanama.vehiclesales.ui.sales

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.components.AddVehicleTextField
import com.avvanama.vehiclesales.ui.components.BackButtonTopBar
import com.avvanama.vehiclesales.ui.vehicle.AddCarViewModel
import com.avvanama.vehiclesales.ui.vehicle.toVehicleDetailsCommon
import kotlinx.coroutines.launch

@Composable
fun AddSales(
    onBackClick: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: AddSalesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val salesUiState = viewModel.salesUiState
    val salesDetails = salesUiState.salesDetails
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsState()

    Column {
        BackButtonTopBar("Add Sales", onBackClick)
//        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Column (
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            val vehicleDetails =
                if (salesDetails.vehicleType == VehicleType.CAR) uiState.value.vehicleDetails.carDetails.toVehicleDetailsCommon()
                else uiState.value.vehicleDetails.motorcycleDetails.toVehicleDetailsCommon()

            Text(text = vehicleDetails.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "${vehicleDetails.stocks} in stock",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.wrapContentWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AddVehicleTextField(label = "Quantity", value = salesDetails.quantity, onValueChange = { viewModel.updateUiState(salesDetails.copy(quantity = it)) }, keyboardType = KeyboardType.Number)

            Spacer(modifier = Modifier.padding(4.dp))
            Row {
                Text(text = "Price", modifier = Modifier.weight(1f))
                Text(text = salesDetails.totalPrice.toString(), modifier = Modifier.weight(1f))
            }
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.addSales()
                    navigateBack()
                }
            },
            enabled = salesUiState.isEntryValid,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add Sales")
        }
    }
}