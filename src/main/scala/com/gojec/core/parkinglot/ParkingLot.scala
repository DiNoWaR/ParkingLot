package com.gojec.core.parkinglot

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

/**
  * Contains and manages parking functionality
  * @param capacity is max capacity of vehicles in parking
  */
class ParkingLot(capacity: Int) {

  private val parkingSlots = Map[Int, Vehicle]()

  private val colorVehicles = Map[String, ArrayBuffer[Vehicle]]()

  private val regNumberSlot = Map[String, Int]()

  /**
    *
    * @param regNumber
    */
  def getSlotByRegNumber(regNumber: String): Int = {
    regNumberSlot.getOrElse(regNumber, throw new Exception())
  }

  /**
    *
    * @param color
    * @return
    */
  def getRegNumbersByColor(color: String): ArrayBuffer[String] = {

    colorVehicles.get(color) match {
      case Some(vehicles) => vehicles.map(vehicle => vehicle.regNumber)
      case None => throw new Exception()
    }
  }


  /**
    *
    * @param vehicle
    */
  def registerVehicle(vehicle: Vehicle): Unit = {
    val slot = allocateSlot()

    parkingSlots += (slot -> vehicle)

    addVehicleToColorVehicles(vehicle)
    addVehicleToRegNumberSlot(slot, vehicle)

    println(s"Allocated slot number: $slot")
  }


  /**
    *
    */
  def unregisterVehicle(slot: Int): Unit = {
    val vehicle = parkingSlots(slot)

    deleteFromColorVehicles(vehicle)
    deleteFromRegNumberSlot(vehicle)

    parkingSlots -= slot
    println(s"Slot number $slot is free")
  }

  private def addVehicleToColorVehicles(vehicle: Vehicle): Unit = {

    colorVehicles.get(vehicle.color) match {
      case Some(vehicles) => vehicles += vehicle

      case None => {
        val vehicles = new ArrayBuffer[Vehicle]()
        vehicles += vehicle
        colorVehicles += (vehicle.color -> vehicles)
      }
    }
  }

  private def addVehicleToRegNumberSlot(slot: Int, vehicle: Vehicle): Unit = {
    regNumberSlot += (vehicle.regNumber -> slot)
  }

  private def deleteFromColorVehicles(vehicle: Vehicle): Unit = {

    colorVehicles.get(vehicle.color) match {

      case Some(vehicles) => {

        vehicles -= vehicle

        if (vehicles.isEmpty) {
          colorVehicles -= vehicle.color
        }
      }

    }
  }

  private def deleteFromRegNumberSlot(vehicle: Vehicle): Unit = {
    regNumberSlot -= vehicle.regNumber
  }


  private def allocateSlot() = 1

}
