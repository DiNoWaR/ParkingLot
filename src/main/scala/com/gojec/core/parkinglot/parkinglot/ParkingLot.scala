package com.gojec.core.parkinglot.parkinglot

import com.gojec.core.parkinglot.vehicle.Vehicle

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Contains and manages parking functionality
  *
  * @param capacity is max capacity of vehicles in parking
  */
class ParkingLot(capacity: Int) {

  private var currentSlot = 0


  private var currentCapacity = 0

  private val parkingSlots = Map[Int, Vehicle]()


  private val colorVehicles = Map[String, ArrayBuffer[Vehicle]]()

  private val colorSlots = Map[String, ArrayBuffer[Int]]()

  private val regNumberSlot = Map[String, Int]()


  def parkVehicle(vehicle: Vehicle): Unit = {

    if (currentCapacity == capacity) {
      println("Sorry, parking lot is full")
    } else {

      val slot = allocateSlot()

      parkingSlots += (slot -> vehicle)

      addVehicleToColorVehicles(vehicle)
      addVehicleToRegNumberSlot(slot, vehicle.regNumber)
      addVehicleColorToColorSlots(slot, vehicle.color)

      currentCapacity += 1
      println(s"Allocated slot number: $slot")

    }

  }

  def unregisterVehicle(slot: Int): Unit = {

    val vgg = parkingSlots.get(slot) match {

      case Some(vehicle) => {
        deleteFromColorVehicles(vehicle)
        deleteFromRegNumberSlot(vehicle)
        deleteFromColorSlots(slot, vehicle)

        parkingSlots -= slot
        currentCapacity -= 1
        println(s"Slot number $slot is free")
      }
      case None => println("Not found")
    }

  }


  def getSlotByRegNumber(regNumber: String): Either[String, Int] = {

    regNumberSlot.get(regNumber) match {

      case Some(slot) => Right(slot)
      case None => Left("Not found")
    }
  }

  def getRegNumbersByColor(color: String): Either[String, ArrayBuffer[String]] = {

    colorVehicles.get(color) match {
      case Some(vehicles) => Right(vehicles.map(vehicle => vehicle.regNumber))
      case None => Left("Not found")
    }
  }

  def getSlotsByColor(color: String): ArrayBuffer[Int] = {

    colorSlots.get(color) match {
      case Some(slots) => slots
    }
  }

  def getStatus(): Map[Int, Vehicle] = {
    parkingSlots
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

  private def addVehicleToRegNumberSlot(slot: Int, regNumber: String): Unit = {
    regNumberSlot += (regNumber -> slot)
  }

  private def addVehicleColorToColorSlots(slot: Int, color: String): Unit = {
    colorSlots.get(color) match {
      case Some(slots) => slots += slot

      case None => {
        val slots = new ArrayBuffer[Int]()
        slots += slot
        colorSlots += (color -> slots)
      }
    }
  }


  private def allocateSlot(): Int = {

    if (parkingSlots.isEmpty) {
      currentSlot += 1
      1
    } else {
      currentSlot += 1
      currentSlot
    }

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

  private def deleteFromColorSlots(slot: Int, vehicle: Vehicle): Unit = {

    colorSlots.get(vehicle.color) match {

      case Some(slots) => {

        slots -= slot

        if (slots.isEmpty) {
          colorSlots -= vehicle.color
        }
      }

    }

  }

}
