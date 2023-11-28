package com.avvanama.vehiclesales.database

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehicleRepository (private val vehicleDao: VehicleDao) {

    suspend fun insertSales(sales: Sales) = vehicleDao.insertSales(sales)

    suspend fun insertCar(car: Car) {
        val vehicle = car as Vehicle
        vehicleDao.insertCar(car, vehicle)
    }

    suspend fun insertMotorcycle(motorcycle: Motorcycle) {
        val vehicle = motorcycle as Vehicle
        vehicleDao.insertMotorcycle(motorcycle, vehicle)
    }

    fun getAllVehicles(): Flow<List<Vehicle>> {
        Log.d("VehicleRepository", "getAllVehicles: ")
        return vehicleDao.getAllVehicles()
    }

    suspend fun updateCarStocks(id: Int, stocks: Int) {
        Log.d("VehicleRepository", "updateCarStocks: $id, $stocks")
        vehicleDao.updateCarStocks(id, stocks)
    }

    suspend fun updateMotorcycleStocks(id: Int, stocks: Int) {
        Log.d("VehicleRepository", "updateMotorcycleStocks: $id, $stocks")
        vehicleDao.updateMotorcycleStocks(id, stocks)
    }

    suspend fun updateStocks(id: Int, stocks: Int, vehicleType: VehicleType) {
        Log.d("VehicleRepository", "updateStocks: $id, $stocks")
        vehicleDao.updateStocks(id, stocks)

        when (vehicleType) {
            VehicleType.CAR -> updateCarStocks(id, stocks)
            VehicleType.MOTORCYCLE -> updateMotorcycleStocks(id, stocks)
        }
    }

    fun getSales(vehicleId: Int): Flow<List<Sales>> = vehicleDao.getSales(vehicleId)

    fun getVehicle(id: Int): Flow<Vehicle> = vehicleDao.getVehicle(id)

    fun getVehicleDetails(id: Int, vehicleType: VehicleType): Flow<Vehicle> {
        Log.d("VehicleRepository", "getVehicleDetails: $id, $vehicleType")
        return when (vehicleType) {
            VehicleType.CAR -> getCar(id)
            VehicleType.MOTORCYCLE -> getMotorcycle(id)
        }
    }

    fun getCar(id: Int): Flow<Car> = vehicleDao.getCar(id)

    fun getMotorcycle(id: Int): Flow<Motorcycle> = vehicleDao.getMotorcycle(id)

    suspend fun deleteVehicle(id: Int) = vehicleDao.deleteVehicle(id)

    suspend fun deleteCar(id: Int) = vehicleDao.deleteCar(id)

    suspend fun deleteMotorcycle(id: Int) = vehicleDao.deleteMotorcycle(id)

    suspend fun updateCar(car: Car) {
        val vehicle = car as Vehicle
        vehicleDao.updateCar(car, vehicle)
    }

    suspend fun updateMotorcycle(motorcycle: Motorcycle) {
        val vehicle = motorcycle as Vehicle
        vehicleDao.updateMotorcycle(motorcycle, vehicle)
    }
}