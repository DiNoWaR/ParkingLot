package com.gojec.core.parkinglot.launcher

import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.runners.{InputFileRunner, InteractiveRunner}

/**
  * Launcher of program.
  * Has 2 runners
  */
object Launcher {

  var parkingLot: ParkingLot = _

  def main(args: Array[String]): Unit = {

    println("|Parking Lot is launched|")
    println("")

    if (args.isEmpty) {
      InteractiveRunner.run()
    }
    else {
      InputFileRunner.run(args(0))
    }

  }
}
