package com.avvanama.vehiclesales.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.avvanama.vehiclesales.VehicleSalesApplication
import com.avvanama.vehiclesales.database.VehicleDao
import com.avvanama.vehiclesales.database.VehicleDatabase
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.database.VehicleType
import com.avvanama.vehiclesales.navigation.Arguments
import com.avvanama.vehiclesales.ui.sales.AddSalesViewModel
import com.avvanama.vehiclesales.ui.sales.SalesViewModel
import com.avvanama.vehiclesales.ui.vehicle.AddCarViewModel
import com.avvanama.vehiclesales.ui.vehicle.AddMotorcycleViewModel
import com.avvanama.vehiclesales.ui.vehicle.VehicleDetailsViewModel
import com.avvanama.vehiclesales.ui.vehicle.VehicleViewModel
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            VehicleViewModel(
                vehicleSalesApplication().container.vehicleRepository
            )
        }
        initializer {
            AddCarViewModel(
                vehicleSalesApplication().container.vehicleRepository
            )
        }
        initializer {
            AddMotorcycleViewModel(
                vehicleSalesApplication().container.vehicleRepository
            )
        }
        initializer {
            VehicleDetailsViewModel(
                this.createSavedStateHandle(),
                vehicleSalesApplication().container.vehicleRepository
            )
        }
        initializer {
            SalesViewModel(
                vehicleSalesApplication().container.vehicleRepository
            )
        }
        initializer {
            AddSalesViewModel(
                this.createSavedStateHandle(),
                vehicleSalesApplication().container.vehicleRepository
            )
        }
    }
}

fun CreationExtras.vehicleSalesApplication(): VehicleSalesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VehicleSalesApplication)