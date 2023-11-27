package com.avvanama.vehiclesales.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehicleRepository (private val vehicleDao: VehicleDao) {

    suspend fun insertCar(car: Car) = vehicleDao.insertCar(car)

    suspend fun insertMotorcycle(motorcycle: Motorcycle) = vehicleDao.insertMotorcycle(motorcycle)

    fun getAllVehicles(): Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    fun getVehicle(id: Int): Flow<Vehicle> = vehicleDao.getVehicle(id)

    fun getCar(id: Int): Flow<Car> = vehicleDao.getCar(id)

    fun getMotorcycle(id: Int): Flow<Motorcycle> = vehicleDao.getMotorcycle(id)

    suspend fun deleteVehicle(id: Int) = vehicleDao.deleteVehicle(id)

    suspend fun deleteCar(id: Int) = vehicleDao.deleteCar(id)

    suspend fun deleteMotorcycle(id: Int) = vehicleDao.deleteMotorcycle(id)
}