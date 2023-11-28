package com.avvanama.vehiclesales.ui.sales

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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.HomeTabs
import com.avvanama.vehiclesales.ui.VehicleSalesBottomBar
import com.avvanama.vehiclesales.ui.components.VehicleList
import com.avvanama.vehiclesales.ui.components.VehicleSalesTopAppBar
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme
import com.avvanama.vehiclesales.ui.vehicle.VehicleViewModel

@Composable
fun Sales(
    modifier: Modifier = Modifier,
    viewModel: SalesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onVehicleSelected: (Int, VehicleType) -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    val vehiclesUiState by viewModel.vehiclesUiState.collectAsState()
    val vehicles = vehiclesUiState.vehicleList

    Scaffold(
        topBar = {
            VehicleSalesTopAppBar("Sales")
        },
        bottomBar = {
            VehicleSalesBottomBar(
                tabs = HomeTabs.values().toList(),
                currentRoute = HomeTabs.SALES.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {
        Column (modifier = Modifier.padding(it)) {
            VehicleList(vehicles = vehicles, onVehicleSelected = onVehicleSelected, modifier = Modifier, actionDescription = "Add Sales")
        }
    }
}