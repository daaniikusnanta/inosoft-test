package com.avvanama.vehiclesales.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertCar(car: Car)

    @Insert
    suspend fun insertMotorcycle(motorcycle: Motorcycle)


}