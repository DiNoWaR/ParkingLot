package com.gojec.core.parkinglot.runners

import com.gojec.core.parkinglot.parser.CommandParser

import scala.io.StdIn.readLine

/**
  * Interactive runner of Parking Lot
  */
object InteractiveRunner {

  def run(): Unit = {

    while (true) {

      val command = readLine()
      CommandParser.parseAndExecute(command)
    }
  }

}
