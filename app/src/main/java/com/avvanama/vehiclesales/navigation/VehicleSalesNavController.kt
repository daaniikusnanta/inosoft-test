package com.avvanama.vehiclesales.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.avvanama.vehiclesales.database.VehicleType

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val ADD_CAR = "add_car"
    const val ADD_MOTORCYCLE = "add_motorcycle"
    const val VEHICLE_DETAILS = "vehicle_details"
    const val VEHICLE_DETAILS_ARG = "${VEHICLE_DETAILS}/{${Arguments.VEHICLE_ID}}/{${Arguments.VEHICLE_TYPE}}"
}

object Arguments {
    const val VEHICLE_ID = "vehicleId"
    const val VEHICLE_TYPE = "vehicleType"
}

@Composable
fun rememberVehicleSalesNavController(
    navController: NavHostController = rememberNavController(),
): VehicleSalesNavController = remember(navController) {
    VehicleSalesNavController(navController)
}

@Stable
class VehicleSalesNavController(
    val navController: NavHostController,
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToAddCar() {
        navController.navigate(MainDestinations.ADD_CAR)
    }

    fun navigateToAddMotorcycle() {
        navController.navigate(MainDestinations.ADD_MOTORCYCLE)
    }

    fun navigateToVehicleDetails(vehicleId: Int, vehicleType: VehicleType) {
        Log.d("navigateToVehicleDetails", "${MainDestinations.VEHICLE_DETAILS}/${vehicleId}/${vehicleType.name}")
        navController.navigate("${MainDestinations.VEHICLE_DETAILS}/${vehicleId}/${vehicleType.name}")
    }
}