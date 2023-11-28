package com.avvanama.vehiclesales.ui.vehicle

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.R
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleDatabase
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.di.AppViewModelProvider
import com.avvanama.vehiclesales.ui.HomeTabs
import com.avvanama.vehiclesales.ui.VehicleSalesBottomBar
import com.avvanama.vehiclesales.ui.components.VehicleList
import com.avvanama.vehiclesales.ui.components.VehicleSalesTopAppBar
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vehicle(
    modifier: Modifier = Modifier,
    viewModel: VehicleViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToAddCar: () -> Unit,
    navigateToAddMotorcycle: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
    onVehicleSelected: (Int, VehicleType) -> Unit
) {
    val vehiclesUiState by viewModel.vehiclesUiState.collectAsState()
    val vehicles = vehiclesUiState.vehicleList
    Log.d("Vehicle", "Vehicle: ${vehiclesUiState.vehicleList}")

    Scaffold(
        topBar = {
            VehicleSalesTopAppBar("Vehicles")
        },
        bottomBar = {
            VehicleSalesBottomBar(
                tabs = HomeTabs.values().toList(),
                currentRoute = HomeTabs.VEHICLE.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) {
        Column (modifier = Modifier.padding(it)) {
            AddVehicleButton(
                navigateToAddCar = navigateToAddCar,
                navigateToAddMotorcycle = navigateToAddMotorcycle,
                modifier = Modifier.padding(top = 8.dp)
            )
            VehicleList(vehicles = vehicles, onVehicleSelected = onVehicleSelected, modifier = Modifier, actionDescription = "Manage Details")
        }
    }
}

@Composable
fun AddVehicleButton(
    navigateToAddCar: () -> Unit,
    navigateToAddMotorcycle: () -> Unit,
    modifier: Modifier
) {
    Row(modifier = modifier.padding(8.dp)) {
        Button(
            onClick = navigateToAddCar,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(painterResource(id = R.drawable.round_directions_car_24), "")
            Text(text = "Add Car", modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = navigateToAddMotorcycle,
            modifier = Modifier.weight(1f)
        ) {
            Icon(painterResource(id = R.drawable.round_two_wheeler_24), "")
            Text(text = "Add Motorcycle", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Preview
@Composable
fun AddVehicleButtonPreview() {
    VehicleSalesTheme {
        AddVehicleButton({}, {}, Modifier)
    }
}