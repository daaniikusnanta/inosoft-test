package com.avvanama.vehiclesales.database

import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val vehicleDao: VehicleDao) {

    suspend fun insertCar(car: Car) = vehicleDao.insertCar(car)

    suspend fun insertMotorcycle(motorcycle: Motorcycle) = vehicleDao.insertMotorcycle(motorcycle)

    suspend fun getAllVehicles(): Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun getVehicle(id: Int): Flow<Vehicle> = vehicleDao.getVehicle(id)

    suspend fun getCar(id: Int): Flow<Car> = vehicleDao.getCar(id)

    suspend fun getMotorcycle(id: Int): Flow<Motorcycle> = vehicleDao.getMotorcycle(id)

    suspend fun deleteVehicle(id: Int) = vehicleDao.deleteVehicle(id)

    suspend fun deleteCar(id: Int) = vehicleDao.deleteCar(id)

    suspend fun deleteMotorcycle(id: Int) = vehicleDao.deleteMotorcycle(id)
}