package com.gojec.core.parkinglot.launch

import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.runners.InteractiveRunner


object Launcher {

  var parkingLot: ParkingLot = _

  def main(args: Array[String]): Unit = {

    if (args.isEmpty) {
      InteractiveRunner.run()
    }


  }
}
