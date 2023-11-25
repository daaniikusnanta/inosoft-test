package com.avvanama.vehiclesales.database

import androidx.room.Entity
import androidx.room.PrimaryKey

open class Vehicle {
    var name: String = ""
    var year: Int = 0
    var color: String = ""
    var price: Double = 0.0
}

@Entity(tableName = "motorcycle")
data class Motorcycle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var machine: String,
    var suspension: String,
    var transmission: String,
) : Vehicle()

@Entity(tableName = "car")
public data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var machine: String,
    var passengerCapacity: Int,
    var type: String,
) : Vehicle()