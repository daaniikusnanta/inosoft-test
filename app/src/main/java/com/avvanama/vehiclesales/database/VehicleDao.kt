package com.avvanama.vehiclesales.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertCar(car: Car)

    @Insert
    suspend fun insertMotorcycle(motorcycle: Motorcycle)

    @Query("SELECT * FROM vehicle")
    suspend fun getAllVehicles(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicle WHERE id = :id")
    suspend fun getVehicle(id: Int): Flow<Vehicle>

    @Query("SELECT * FROM car WHERE id = :id")
    suspend fun getCar(id: Int): Flow<Car>

    @Query("SELECT * FROM motorcycle WHERE id = :id")
    suspend fun getMotorcycle(id: Int): Flow<Motorcycle>

    @Query("DELETE FROM vehicle WHERE id = :id")
    suspend fun deleteVehicle(id: Int)

    @Query("DELETE FROM car WHERE id = :id")
    suspend fun deleteCar(id: Int)

    @Query("DELETE FROM motorcycle WHERE id = :id")
    suspend fun deleteMotorcycle(id: Int)
}