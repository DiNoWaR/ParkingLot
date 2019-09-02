package com.gojec.core.parkinglot

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

/**
  * Contains and manages parking functionality
  *
  * @param capacity is max capacity of vehicles in parking
  */
class ParkingLot(capacity: Int) {

  private val parkingSlots = Map[Int, Vehicle]()


  private val colorVehicles = Map[String, ArrayBuffer[Vehicle]]()

  private val colorSlots = Map[String, ArrayBuffer[Int]]()

  private val regNumberSlot = Map[String, Int]()


  def registerVehicle(vehicle: Vehicle): Unit = {
    val slot = allocateSlot()

    parkingSlots += (slot -> vehicle)

    addVehicleToColorVehicles(vehicle)
    addVehicleToRegNumberSlot(slot, vehicle.regNumber)
    addVehicleColorToColorSlots(slot, vehicle.color)

    println(s"Allocated slot number: $slot")
  }

  def unregisterVehicle(slot: Int): Unit = {
    val vehicle = parkingSlots(slot)

    deleteFromColorVehicles(vehicle)
    deleteFromRegNumberSlot(vehicle)
    deleteFromColorSlots(slot, vehicle)

    parkingSlots -= slot
    println(s"Slot number $slot is free")
  }


  def getSlotByRegNumber(regNumber: String): Int = {
    regNumberSlot.getOrElse(regNumber, throw new Exception())
  }

  def getRegNumbersByColor(color: String): ArrayBuffer[String] = {

    colorVehicles.get(color) match {
      case Some(vehicles) => vehicles.map(vehicle => vehicle.regNumber)
    }
  }

  def getSlotsByColor(color: String): ArrayBuffer[Int] = {

    colorSlots.get(color) match {
      case Some(slots) => slots
    }
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


  private def allocateSlot() = 1

}
