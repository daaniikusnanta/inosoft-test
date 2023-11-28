package com.avvanama.vehiclesales.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleSalesTopAppBar(
    title: String = ""
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