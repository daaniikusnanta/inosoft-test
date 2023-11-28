package com.avvanama.vehiclesales

import android.app.Application
import com.avvanama.vehiclesales.database.AppContainer
import com.avvanama.vehiclesales.database.AppDataContainer
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

class VehicleSalesApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}