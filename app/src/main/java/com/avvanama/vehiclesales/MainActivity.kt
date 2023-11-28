package com.avvanama.vehiclesales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import com.avvanama.vehiclesales.navigation.MainDestinations
import com.avvanama.vehiclesales.navigation.rememberVehicleSalesNavController
import com.avvanama.vehiclesales.ui.HomeTabs
import com.avvanama.vehiclesales.ui.home
import com.avvanama.vehiclesales.ui.reports.ReportDetails
import com.avvanama.vehiclesales.ui.sales.AddSales
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme
import com.avvanama.vehiclesales.ui.vehicle.AddCar
import com.avvanama.vehiclesales.ui.vehicle.AddMotorcycle
import com.avvanama.vehiclesales.ui.vehicle.Vehicle
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetails
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VehicleSalesApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleSalesApp() {
    VehicleSalesTheme {
        val vehicleSalesNavController = rememberVehicleSalesNavController()
        NavHost(
            navController = vehicleSalesNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE,
        ) {
            vehicleSalesNavGraph(
                upPress = vehicleSalesNavController::upPress,
                navigateToAddCar = vehicleSalesNavController::navigateToAddCar,
                navigateToAddMotorcycle = vehicleSalesNavController::navigateToAddMotorcycle,
                navigateToVehicleDetails = vehicleSalesNavController::navigateToVehicleDetails,
                navigateToAddSales = vehicleSalesNavController::navigateToAddSales,
                navigateToReportDetails = vehicleSalesNavController::navigateToReportDetails,
                navigateToBottomBarRoute = vehicleSalesNavController::navigateToBottomBarRoute,
                navigateBack = vehicleSalesNavController::navigateBack,
            )
        }
    }
}

private fun NavGraphBuilder.vehicleSalesNavGraph(
    upPress: () -> Unit,
    navigateToAddCar: () -> Unit,
    navigateToAddMotorcycle: () -> Unit,
    navigateToVehicleDetails: (Int, VehicleType) -> Unit,
    navigateToAddSales: (Int, VehicleType) -> Unit,
    navigateToReportDetails: (Int, VehicleType) -> Unit,
    navigateToBottomBarRoute: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeTabs.REPORT.route,
    ) {
        home(
            navigateToAddCar = navigateToAddCar,
            navigateToAddMotorcycle = navigateToAddMotorcycle,
            navigateToVehicleDetails = navigateToVehicleDetails,
            navigateToAddSales = navigateToAddSales,
            navigateToReportDetails = navigateToReportDetails,
            navigateToBottomBarRoute = navigateToBottomBarRoute
        )
    }
    composable(MainDestinations.ADD_CAR) {
        AddCar(navigateBack, upPress)
    }
    composable(MainDestinations.ADD_MOTORCYCLE) {
        AddMotorcycle(navigateBack, upPress)
    }
    composable(
        route = MainDestinations.VEHICLE_DETAILS_ARG,
        arguments = listOf(
            navArgument(Arguments.VEHICLE_ID) {
                type = NavType.IntType
            },
            navArgument(Arguments.VEHICLE_TYPE) {
                type = NavType.EnumType(VehicleType::class.java)
            }
        )
    ) {
        VehicleDetails(upPress)
    }
    composable(
        route = MainDestinations.ADD_SALES_ARG,
        arguments = listOf(
            navArgument(Arguments.VEHICLE_ID) {
                type = NavType.IntType
            },
            navArgument(Arguments.VEHICLE_TYPE) {
                type = NavType.EnumType(VehicleType::class.java)
            }
        )
    ) {
        AddSales(onBackClick = upPress, navigateBack = navigateBack)
    }
    composable(
        route = MainDestinations.REPORT_DETAILS_ARG,
        arguments = listOf(
            navArgument(Arguments.VEHICLE_ID) {
                type = NavType.IntType
            },
            navArgument(Arguments.VEHICLE_TYPE) {
                type = NavType.EnumType(VehicleType::class.java)
            }
        )
    ) {
        ReportDetails(onBackClick = upPress, navigateBack = navigateBack)
    }
}