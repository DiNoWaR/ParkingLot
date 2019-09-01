package com.gojec.parkinglot

object Vgg {

  def main(args: Array[String]): Unit = {

    val parkingLot = new ParkingLot(10)

    println(parkingLot.parkingSlots.size)

  }

}
