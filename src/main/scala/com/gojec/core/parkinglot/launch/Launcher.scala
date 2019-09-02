package com.gojec.core.parkinglot.launch

import com.gojec.core.parkinglot.command.Commands
import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.parser.CommandParser
import com.gojec.core.parkinglot.vehicle.Vehicle

import scala.io.StdIn.readLine


object Launcher {

  var parkingSlot: ParkingLot = _

  def main(args: Array[String]): Unit = {

    args.foreach(command => {

      CommandParser.parseCommand(readLine(command)) match {

        case Left(invalid) => println(invalid)

        case Right(parsedCommand) => parsedCommand._1 match {

          case Commands.CreateParkingLot.message => parkingSlot = new ParkingLot(parsedCommand._2.head.toInt)

          case Commands.Park.message => parkingSlot.parkVehicle(Vehicle(parsedCommand._2.head, parsedCommand._2(1)))

          case Commands.Leave.message => parkingSlot.unregisterVehicle(parsedCommand._2.head.toInt)

          case Commands.Status.message => println("Ups")

          case Commands.RegNumbersByColor.message => {
            parkingSlot.getRegNumbersByColor(parsedCommand._2.head).foreach(regNumber => print(s"$regNumber, "))
          }

          case Commands.SlotsByColor.message => {
            val vff = parkingSlot.getSlotByRegNumber(parsedCommand._2.head)
          }
        }
      }
    })

  }
}
