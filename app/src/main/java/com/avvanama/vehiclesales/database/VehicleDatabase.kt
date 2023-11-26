package com.avvanama.vehiclesales.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class, Car::class, Motorcycle::class], version = 1)
abstract class VehicleDatabase: RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var Instance: VehicleDatabase? = null

        fun getDatabase(context: Context): VehicleDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VehicleDatabase::class.java, "vehicle_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}