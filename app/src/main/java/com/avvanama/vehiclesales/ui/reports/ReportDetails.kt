package com.avvanama.vehiclesales.ui.reports

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Sales
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.components.AddVehicleTextField
import com.avvanama.vehiclesales.ui.components.BackButtonTopBar
import com.avvanama.vehiclesales.ui.sales.AddSalesViewModel
import com.avvanama.vehiclesales.ui.vehicle.toVehicleDetailsCommon
import kotlinx.coroutines.launch

@Composable
fun ReportDetails(
    onBackClick: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: ReportDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val salesUiState = viewModel.salesUiState.collectAsState()
    val salesDetails = salesUiState.value
    val uiState = viewModel.uiState.collectAsState()

    Scaffold (
        topBar = { BackButtonTopBar("Add Sales", onBackClick) }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ) {
//        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Column (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                val vehicleDetails =
                    if (uiState.value.vehicleType == VehicleType.CAR) uiState.value.vehicleDetails.carDetails.toVehicleDetailsCommon()
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

                ReportList(salesList = salesDetails.salesList)
            }
        }
    }
}

@Composable
fun ReportList(
    salesList: List<Sales>,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        Text(text = "Sales Report", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))

        if (salesList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                item { Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars)) }
                items(items = salesList, key = { it.id }) { sales ->
                    Spacer(modifier = Modifier.height(8.dp))
                    ReportItem(sales = sales)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    Icons.Rounded.Warning,
                    contentDescription = "Account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Text(
                    text = "No sales found",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}




@Composable
fun ReportItem(
    sales: Sales,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${sales.quantity} units",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = sales.date,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }
            Column (
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = "Total Sales",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = sales.totalPrice.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}