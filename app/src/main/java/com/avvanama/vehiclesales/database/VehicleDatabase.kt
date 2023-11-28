package com.avvanama.vehiclesales.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class, Car::class, Motorcycle::class, Sales::class], version = 1, exportSchema = false)
abstract class VehicleDatabase: RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var instance: VehicleDatabase? = null

        fun getDatabase(context: Context): VehicleDatabase {
            return instance ?: synchronized(this) {
                Log.d("VehicleDatabase", "getDatabase: ")
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDatabase::class.java,
                    "vehicle_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}