package com.avvanama.vehiclesales.ui.components

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avvanama.vehiclesales.R
import com.avvanama.vehiclesales.database.Car
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme

@Composable
fun VehicleList(
    vehicles: List<Vehicle>,
    onVehicleSelected: (Int, VehicleType) -> Unit,
    modifier: Modifier = Modifier,
    actionDescription: String
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
                    VehicleItem(vehicle = vehicle, onVehicleSelected = onVehicleSelected, actionDescription = actionDescription)
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
fun VehicleItem(
    vehicle: Vehicle,
    actionDescription: String = "See Details",
    onVehicleSelected: (Int, VehicleType) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onVehicleSelected(vehicle.id, vehicle.vehicleType) },
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
                if (vehicle.vehicleType == VehicleType.CAR) painterResource(R.drawable.round_directions_car_24)
                else painterResource(R.drawable.round_two_wheeler_24),
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
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${vehicle.year} - ${vehicle.color}",
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
                    text = "${vehicle.stocks} in stock",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                ) {
                    Text(
                        text = actionDescription,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                    )
                    Icon(
                        Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}