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
import com.avvanama.vehiclesales.navigation.MainDestinations
import com.avvanama.vehiclesales.navigation.rememberVehicleSalesNavController
import com.avvanama.vehiclesales.ui.HomeTabs
import com.avvanama.vehiclesales.ui.home
import com.avvanama.vehiclesales.ui.theme.VehicleSalesTheme
import com.avvanama.vehiclesales.ui.vehicle.AddCar
import com.avvanama.vehiclesales.ui.vehicle.Vehicle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                navigateBack = vehicleSalesNavController::navigateBack,
            )
        }
    }
}

private fun NavGraphBuilder.vehicleSalesNavGraph(
    upPress: () -> Unit,
    navigateToAddCar: () -> Unit,
    navigateBack: () -> Unit,
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeTabs.VEHICLE.route,
    ) {
        home(
            navigateToAddCar = navigateToAddCar,
            onVehicleSelected = { TODO() }
        )
    }
    composable(MainDestinations.ADD_CAR) {
        AddCar(navigateBack, upPress)
    }
}