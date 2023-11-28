package com.avvanama.vehiclesales.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow

enum class VehicleType {
    CAR,
    MOTORCYCLE
}

@Entity(tableName = "vehicle")
open class Vehicle {
    @PrimaryKey(autoGenerate = true)
    open var id: Int = 0
    open var name: String = ""
    open var year: Int = 0
    open var color: String = ""
    open var price: Double = 0.0
    open var stocks: Int = 0
    open var vehicleType: VehicleType = VehicleType.CAR
}

@Entity(tableName = "motorcycle")
data class Motorcycle(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override var name: String = "",
    override var year: Int = 0,
    override var color: String = "",
    override var price: Double = 0.0,
    override var stocks: Int = 0,
    override var vehicleType: VehicleType = VehicleType.MOTORCYCLE,
    var machine: String,
    var suspension: String,
    var transmission: String,
) : Vehicle()


@Entity(tableName = "car")
data class Car(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override var name: String = "",
    override var year: Int = 0,
    override var color: String = "",
    override var price: Double = 0.0,
    override var stocks: Int = 0,
    override var vehicleType: VehicleType = VehicleType.CAR,
    var machine: String,
    var passengerCapacity: Int,
    var type: String,
) : Vehicle()

@Entity(tableName = "sales")
data class Sales(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vehicleId: Int,
    val vehicleType: VehicleType,
    val quantity: Int,
    val totalPrice: Double,
    val date: String,
)