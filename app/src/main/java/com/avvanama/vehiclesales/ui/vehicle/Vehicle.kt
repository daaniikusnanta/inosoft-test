package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Motorcycle
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleDatabase
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Vehicle(
    modifier: Modifier = Modifier,
    viewModel: VehicleViewModel = hiltViewModel(),
    onVehicleSelected: (Vehicle) -> Unit
) {
    val vehicles = viewModel.vehicles.collectAsState()

    Scaffold(
        topBar = {
            VehicleSalesTopAppBar("Vehicles")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { TODO() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = modifier
    ) { it ->
        if (vehicles.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item { Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars)) }
                items(vehicles.value) { vehicle ->
                    Spacer(modifier = Modifier.height(8.dp))
                    VehicleItem(vehicle = vehicle)
                }
            }
        } else {
            Column(
                modifier = Modifier
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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleSalesTopAppBar(
    title: String = "Test"
) {
    VehicleSalesTheme {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            title = {
                Text(text = title)
            }
        )
    }

}

@Composable
fun VehicleItem(
    vehicle: Vehicle
) {
    Surface(
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if(vehicle is Car) Icons.Outlined.Star else Icons.Outlined.Build,
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
                    text = "${vehicle.year} left",
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

@Preview
@Composable
fun VehicleItemPreview() {
    VehicleSalesTheme {
        val car = Car(
            "Gas",
            4,
            "Gasoline"
        ).apply {
            name = "Toyota Supra"
            color = "White"
            year = 1998
            price = 500000000.0
        }
        VehicleItem(vehicle = car)
    }
}