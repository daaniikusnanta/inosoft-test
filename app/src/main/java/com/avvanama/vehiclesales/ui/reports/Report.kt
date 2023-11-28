package com.avvanama.vehiclesales.ui.reports

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.HomeTabs
import com.avvanama.vehiclesales.ui.VehicleSalesBottomBar
import com.avvanama.vehiclesales.ui.components.VehicleSalesTopAppBar
import com.avvanama.vehiclesales.ui.sales.SalesViewModel

@Composable
fun Report(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onVehicleSelected: (Int, VehicleType) -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    val vehiclesUiState by viewModel.vehiclesUiState.collectAsState()
    val vehicles = vehiclesUiState.vehicleList

    Scaffold(
        topBar = {
            VehicleSalesTopAppBar("Report")
        },
        bottomBar = {
            VehicleSalesBottomBar(
                tabs = HomeTabs.values().toList(),
                currentRoute = HomeTabs.REPORT.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {
        Column (modifier = Modifier.padding(it)) {
            VehicleList(vehicles = vehicles, onVehicleSelected = onVehicleSelected, modifier = Modifier)
        }
    }
}

@Composable
fun VehicleList(
    vehicles: List<Vehicle>,
    onVehicleSelected: (Int, VehicleType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
    ) {
        Text(text = "Vehicles", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        if (vehicles.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                item { Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars)) }
                items(items = vehicles, key = { it.id }) { vehicle ->
                    Spacer(modifier = Modifier.height(8.dp))
                    SalesItem(vehicle = vehicle, onVehicleSelected = onVehicleSelected)
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
                    text = "No vehicles found",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}




@Composable
fun SalesItem(
    vehicle: Vehicle,
    onVehicleSelected: (Int, VehicleType) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                if (vehicle.stocks > 0) onVehicleSelected(vehicle.id, vehicle.vehicleType)
                else Log.d("SalesItem", "No stocks left")
            },
        shape = MaterialTheme.shapes.medium,
        color = if (vehicle.stocks > 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (vehicle.vehicleType == VehicleType.CAR) Icons.Outlined.Star else Icons.Outlined.Build,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = vehicle.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = vehicle.year.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${vehicle.stocks} left",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                )
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentDescription = null,
                )
            }
        }
    }
}