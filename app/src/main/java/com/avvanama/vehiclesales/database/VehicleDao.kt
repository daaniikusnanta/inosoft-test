package com.avvanama.vehiclesales.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertSales(sales: Sales)

    @Insert
    suspend fun insertCar(vehicle: Vehicle, car: Car)

    @Insert
    suspend fun insertMotorcycle(vehicle: Vehicle, motorcycle: Motorcycle)

    @Query("SELECT * FROM vehicle")
    fun getAllVehicles(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicle WHERE id = :id")
    fun getVehicle(id: Int): Flow<Vehicle>

    @Query("SELECT * FROM car WHERE id = :id")
    fun getCar(id: Int): Flow<Car>

    @Query("SELECT * FROM motorcycle WHERE id = :id")
    fun getMotorcycle(id: Int): Flow<Motorcycle>

    @Query("UPDATE vehicle SET stocks = :stocks WHERE id = :id")
    suspend fun updateStocks(id: Int, stocks: Int)

    @Query("UPDATE car SET stocks = :stocks WHERE id = :id")
    suspend fun updateCarStocks(id: Int, stocks: Int)

    @Query("UPDATE motorcycle SET stocks = :stocks WHERE id = :id")
    suspend fun updateMotorcycleStocks(id: Int, stocks: Int)

    @Query("SELECT COUNT(*) FROM sales WHERE vehicleId = :vehicleId")
    fun getSalesCount(vehicleId: Int): Int

    @Query("SELECT * FROM sales WHERE vehicleId = :vehicleId")
    fun getSales(vehicleId: Int): Flow<List<Sales>>

    @Query("DELETE FROM vehicle WHERE id = :id")
    suspend fun deleteVehicle(id: Int)

    @Query("DELETE FROM car WHERE id = :id")
    suspend fun deleteCar(id: Int)

    @Query("DELETE FROM motorcycle WHERE id = :id")
    suspend fun deleteMotorcycle(id: Int)
}