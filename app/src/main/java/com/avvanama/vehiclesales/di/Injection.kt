package com.avvanama.vehiclesales.di

import android.content.Context
import androidx.room.Room
import com.avvanama.vehiclesales.database.VehicleDao
import com.avvanama.vehiclesales.database.VehicleDatabase
import com.avvanama.vehiclesales.database.VehicleRepository
import com.avvanama.vehiclesales.ui.vehicle.AddCarViewModel
import com.avvanama.vehiclesales.ui.vehicle.VehicleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideVehicleRepository(@ApplicationContext context: Context): VehicleRepository {
        return VehicleRepository(VehicleDatabase.getDatabase(context).vehicleDao())
    }

    @Provides
    @Singleton
    fun provideVehicleViewModel(vehicleRepository: VehicleRepository): VehicleViewModel {
        return VehicleViewModel(vehicleRepository)
    }

    @Provides
    @Singleton
    fun provideAddCarViewModel(vehicleRepository: VehicleRepository): AddCarViewModel {
        return AddCarViewModel(vehicleRepository)
    }
}