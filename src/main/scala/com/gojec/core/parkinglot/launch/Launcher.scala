package com.gojec.core.parkinglot.launch

import com.gojec.core.parkinglot.command.Commands
import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.parser.CommandParser
import com.gojec.core.parkinglot.vehicle.Vehicle

import scala.io.StdIn._


object Launcher {

  var parkingLot: ParkingLot = _

  def main(args: Array[String]): Unit = {

    while (true) {

      val command = readLine()

      CommandParser.parseCommand(command) match {

        case Left(invalid) => println(invalid)

        case Right(parsedCommand) => parsedCommand._1 match {

          case Commands.CreateParkingLot.message => {
            parkingLot = new ParkingLot(parsedCommand._2.head.toInt)
            println(s"Created a parking lot with ${parsedCommand._2.head} slots")
          }

          case Commands.Park.message => parkingLot.parkVehicle(Vehicle(parsedCommand._2.head, parsedCommand._2(1)))

          case Commands.Leave.message => parkingLot.unregisterVehicle(parsedCommand._2.head.toInt)

          case Commands.Status.message => {

            System.out.format("%1s%20s%10s", "Slot No.", "Registration No", "Colour")
            println()

            parkingLot.getStatus().toList.sortBy(_._1).foreach(item => {
              System.out.format("%1s%25s%11s", item._1.toString, item._2.regNumber, item._2.color)
              println()
            })
          }

          case Commands.RegNumbersByColor.message => {

            parkingLot.getRegNumbersByColor(parsedCommand._2.head) match {
              case Left(error) => println(error)

              case Right(regNumbers) => {
                regNumbers.foreach(regNumber => print(s"$regNumber, "))
                println()
              }
            }
          }

          case Commands.SlotsByColor.message => {
            parkingLot.getSlotsByColor(parsedCommand._2.head).foreach(slot => print(s"$slot, "))
            println()
          }

          case Commands.SlotByRegNumber.message => {

            val slot = parkingLot.getSlotByRegNumber(parsedCommand._2.head) match {
              case Left(error) => println(error)
              case Right(slot) => println(slot)
            }

          }

          case Commands.Exit.message => System.exit(0)

        }
      }

    }


  }
}
