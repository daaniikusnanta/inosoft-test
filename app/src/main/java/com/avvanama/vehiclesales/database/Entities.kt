package com.avvanama.vehiclesales.database

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class VehicleType {
    CAR,
    MOTORCYCLE
}

@Entity(tableName = "vehicle")
open class Vehicle {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var year: Int = 0
    var color: String = ""
    var price: Double = 0.0
    var vehicleType: VehicleType = VehicleType.CAR
}

@Entity(tableName = "motorcycle")
data class Motorcycle(
    var machine: String,
    var suspension: String,
    var transmission: String,
) : Vehicle() {
    init {
        vehicleType = VehicleType.MOTORCYCLE
    }
}

@Entity(tableName = "car")
data class Car(
    var machine: String,
    var passengerCapacity: Int,
    var type: String,
) : Vehicle() {
    init {
        vehicleType = VehicleType.CAR
    }
}