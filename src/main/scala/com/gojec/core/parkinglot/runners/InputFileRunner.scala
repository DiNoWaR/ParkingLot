package com.gojec.core.parkinglot.runners

import com.gojec.core.parkinglot.parser.CommandParser

import scala.io.Source

/**
  * Input File runner of Parking Lot
  */
object InputFileRunner {

  def run(inputPath: String): Unit = {

    val commands = Source.fromFile(inputPath).getLines.toList
    commands.foreach(command => CommandParser.parseAndExecute(command))

  }
}
