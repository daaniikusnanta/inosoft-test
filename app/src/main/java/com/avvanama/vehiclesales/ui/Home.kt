package com.avvanama.vehiclesales.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.avvanama.vehiclesales.R
import com.avvanama.vehiclesales.database.Vehicle
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.ui.reports.Report
import com.avvanama.vehiclesales.ui.sales.Sales
import com.avvanama.vehiclesales.ui.vehicle.Vehicle

fun NavGraphBuilder.home(
    navigateToAddCar: () -> Unit,
    navigateToAddMotorcycle: () -> Unit,
    navigateToVehicleDetails: (Int, VehicleType) -> Unit,
    navigateToAddSales: (Int, VehicleType) -> Unit,
    navigateToReportDetails: (Int, VehicleType) -> Unit,
    navigateToBottomBarRoute: (String) -> Unit
) {
    composable(HomeTabs.VEHICLE.route) { from ->
        Vehicle(onVehicleSelected = navigateToVehicleDetails, navigateToAddCar = navigateToAddCar, navigateToAddMotorcycle = navigateToAddMotorcycle, onNavigateToRoute = navigateToBottomBarRoute)
    }
    composable(HomeTabs.SALES.route) { from ->
        Sales(onVehicleSelected = navigateToAddSales, onNavigateToRoute = navigateToBottomBarRoute)
    }
    composable(HomeTabs.REPORT.route) { from ->
        Report(onVehicleSelected = navigateToReportDetails, onNavigateToRoute = navigateToBottomBarRoute)
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

@Composable
fun VehicleSalesBottomBar(
    tabs: List<HomeTabs>,
    currentRoute: String?,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    NavigationBar {
        routes.forEachIndexed {
            index, route ->
            val tab = tabs[index]
            NavigationBarItem(
                icon = { Icon(tab.icon, stringResource(tab.title)) },
                label = { Text(stringResource(tab.title)) },
                selected = currentSection == tab,
                onClick = { navigateToRoute(route) }
            )
        }
    }
}