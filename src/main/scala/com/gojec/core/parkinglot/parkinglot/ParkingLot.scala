package com.gojec.core.parkinglot.parkinglot

import com.gojec.core.parkinglot.vehicle.Vehicle

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Contains and manages parking functionality
  *
  * @param maxCapacity is max capacity of vehicles in parking
  */
class ParkingLot(maxCapacity: Int) {

  private var currentSlot = 1

  private var currentCapacity = 0

  private val parkingSlots = Map[Int, Vehicle]()


  private val colorVehicles = Map[String, ArrayBuffer[Vehicle]]()

  private val colorSlots = Map[String, ArrayBuffer[Int]]()

  private val regNumberSlot = Map[String, Int]()

  private val lru: LRU = new LRU(maxCapacity)


  def parkVehicle(vehicle: Vehicle): Either[Unit, Unit] = {

    if (currentCapacity == maxCapacity) {
      Left(println("Sorry, parking lot is full"))

    } else {

      val slot = allocateSlot()

      parkingSlots += (slot -> vehicle)

      addVehicleToColorVehicles(vehicle)
      addVehicleToRegNumberSlot(slot, vehicle.regNumber)
      addVehicleColorToColorSlots(slot, vehicle.color)

      currentCapacity += 1
      Right(println(s"Allocated slot number: $slot"))

    }

  }

  def unregisterVehicle(slot: Int): Unit = {

    parkingSlots.get(slot) match {

      case Some(vehicle) => {
        deleteFromColorVehicles(vehicle)
        deleteFromRegNumberSlot(vehicle)
        deleteFromColorSlots(slot, vehicle)
        lru.checkAndUpdateMinFreeSlot(slot)

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

  def getSlotsByColor(color: String): Either[String, ArrayBuffer[Int]] = {

    colorSlots.get(color) match {
      case Some(slots) => Right(slots)
      case None => Left("Not found")
    }
  }

  def getStatus(): Map[Int, Vehicle] = {
    parkingSlots
  }

  def getCurrentCapacity(): Int = {
    currentCapacity
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
    lru.getMinFreeSlot()
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
