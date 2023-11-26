package com.avvanama.vehiclesales.ui.vehicle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AddCar() {
    val scrollState = rememberScrollState()

    Column {
        AddCarTopBar("Add Car")
//        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Column (
            modifier = Modifier
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            AddVehicleTextField(label = "Name", value = "", onValueChange = { })

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Year", value = "", onValueChange = { }, keyboardType = KeyboardType.Number)

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Color", value = "", onValueChange = {})

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Machine", value = "", onValueChange = {})

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Passenger Capacity", value = "", onValueChange = {}, keyboardType = KeyboardType.Number)

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Type", value = "", onValueChange = {})

            Spacer(modifier = Modifier.padding(16.dp))
            AddVehicleTextField(label = "Price", value = "", onValueChange = {}, keyboardType = KeyboardType.Number)

            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Current Stocks", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.padding(4.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                TextField(
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Add Car")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    Text(text = label, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.padding(4.dp))
    TextField(
        value = "",
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddCarTopBar(
    title: String = "tes",
    onBackClick: () -> Unit = { TODO() }
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        }
    )
}