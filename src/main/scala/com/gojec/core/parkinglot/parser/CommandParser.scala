package com.gojec.core.parkinglot.parser

import com.gojec.core.parkinglot.command.Commands
import com.gojec.core.parkinglot.launcher.Launcher.parkingLot
import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.vehicle.Vehicle

/**
  * Parser of parking lots commands
  */
object CommandParser {

  def parseAndExecute(command: String) = {

    val splittedCommand = command.split(" ")

    splittedCommand.head match {

      case Commands.CreateParkingLot.message => {
        parkingLot = new ParkingLot(splittedCommand(1).toInt)
        println(s"Created a parking lot with ${splittedCommand(1)} slots")
      }

      case Commands.Park.message => parkingLot.parkVehicle(Vehicle(splittedCommand(1), splittedCommand(2)))

      case Commands.Leave.message => parkingLot.unregisterVehicle(splittedCommand(1).toInt)

      case Commands.Status.message => {

        System.out.format("%1s%20s%10s", "Slot No.", "Registration No", "Colour")
        println()

        parkingLot.getStatus().toList.sortBy(_._1).foreach(item => {
          System.out.format("%1s%25s%11s", item._1.toString, item._2.regNumber, item._2.color)
          println()
        })
      }

      case Commands.RegNumbersByColor.message => {

        parkingLot.getRegNumbersByColor(splittedCommand(1)) match {
          case Left(error) => println(error)

          case Right(regNumbers) => {
            regNumbers.foreach(regNumber => print(s"$regNumber, "))
            println()
          }
        }
      }

      case Commands.SlotsByColor.message => {
        parkingLot.getSlotsByColor(splittedCommand(1)).foreach(slots => slots.foreach(slot => print(s"$slot, ")))
        println()
      }

      case Commands.SlotByRegNumber.message => {

        val slot = parkingLot.getSlotByRegNumber(splittedCommand(1)) match {
          case Left(error) => println(error)
          case Right(slot) => println(slot)
        }

      }

      case Commands.Exit.message => System.exit(0)


    }

  }

}