package com.avvanama.vehiclesales.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.avvanama.vehiclesales.R
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.ui.vehicle.Vehicle

fun NavGraphBuilder.home(
    navigateToAddCar: () -> Unit,
    navigateToAddMotorcycle: () -> Unit,
    onVehicleSelected: (Vehicle) -> Unit,
) {
    composable(HomeTabs.VEHICLE.route) { from ->
        Vehicle(onVehicleSelected = onVehicleSelected, navigateToAddCar = navigateToAddCar, navigateToAddMotorcycle = navigateToAddMotorcycle)
    }
    composable(HomeTabs.SALES.route) { from ->
        TODO()
    }
    composable(HomeTabs.REPORT.route) { from ->
        TODO()
    }
}

enum class HomeTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    VEHICLE(R.string.vehicle, Icons.Rounded.Warning, HomeDestinations.VEHICLE_ROUTE),
    SALES(R.string.sales, Icons.Rounded.Warning, HomeDestinations.SALES_ROUTE),
    REPORT(R.string.report, Icons.Rounded.Warning, HomeDestinations.REPORT_ROUTE)
}

private object HomeDestinations {
    const val VEHICLE_ROUTE = "home/vehicle"
    const val SALES_ROUTE = "home/sales"
    const val REPORT_ROUTE = "home/report"
}