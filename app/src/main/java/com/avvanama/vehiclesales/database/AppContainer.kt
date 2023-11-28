package com.avvanama.vehiclesales.database

import android.content.Context

interface AppContainer {
    val vehicleRepository: VehicleRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val vehicleRepository: VehicleRepository by lazy {
        VehicleRepository(VehicleDatabase.getDatabase(context).vehicleDao())
    }
}