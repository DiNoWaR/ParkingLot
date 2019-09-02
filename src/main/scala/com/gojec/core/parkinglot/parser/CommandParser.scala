package com.gojec.core.parkinglot.parser

import com.gojec.core.parkinglot.command.Commands


object CommandParser {

  def parseCommand(command: String): Either[String, (String, Vector[String])] = {

    val result = command.split(" ")

    result.head match {
      case Commands.CreateParkingLot.message
        || Commands.Park.message
        || Commands.Leave.message
        || Commands.RegNumbersByColor.message
        || Commands.SlotByRegNumber.message
        || Commands.SlotsByColor.message
        || Commands.Status.message
        || Commands.Exit.message => Right((result.head, result.tail.toVector))

      case _ => Left(Commands.InvalidCommand.message)

    }
  }

}