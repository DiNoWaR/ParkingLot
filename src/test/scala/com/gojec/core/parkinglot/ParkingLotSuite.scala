package com.gojec.core.parkinglot

import com.gojec.core.parkinglot.parkinglot.ParkingLot
import com.gojec.core.parkinglot.vehicle.Vehicle
import org.scalatest.FunSuite

class ParkingLotSuite extends FunSuite {

  test("current capacity should be 0") {
    val parkingLot = new ParkingLot(3)
    assert(parkingLot.getCurrentCapacity() == 0)
  }

  test("current capacity should be 0 because of removing vehicle") {
    val parkingLot = new ParkingLot(3)

    parkingLot.parkVehicle(Vehicle("KA-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("KA-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))

    parkingLot.unregisterVehicle(1)
    parkingLot.unregisterVehicle(2)
    parkingLot.unregisterVehicle(3)

    assert(parkingLot.getCurrentCapacity() == 0)
  }

  test("current capacity should be 3") {
    val parkingLot = new ParkingLot(3)

    parkingLot.parkVehicle(Vehicle("KA-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("KA-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))

    assert(parkingLot.getCurrentCapacity() == 3)
  }

  test("current capacity should be 2 because of removing vehicle") {
    val parkingLot = new ParkingLot(3)

    parkingLot.parkVehicle(Vehicle("KA-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("KA-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))

    parkingLot.unregisterVehicle(1)

    assert(parkingLot.getCurrentCapacity() == 2)
  }

  test("vehicle should not be parked and current capacity should be equal to max capacity") {
    val parkingLot = new ParkingLot(3)

    parkingLot.parkVehicle(Vehicle("KA-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("KA-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Yellow"))

    assert(parkingLot.getCurrentCapacity() == 3)
  }

  test("reg numbers should be returned correctly") {
    val parkingLot = new ParkingLot(7)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))
    parkingLot.parkVehicle(Vehicle("GF-04-TR-7754", "Yellow"))

    assert(parkingLot.getRegNumbersByColor("Green").map(item => item.toList).right.get.sorted == List("KA-02-MH-5521", "KA-01-TH-4321").sorted)
    assert(parkingLot.getRegNumbersByColor("Red").map(item => item.toList).right.get.sorted == List("TT-04-AH-6788", "AM-02-MH-5521").sorted)
    assert(parkingLot.getRegNumbersByColor("Blue").map(item => item.toList).right.get.sorted == List("AK-03-MH-5566", "DF-77-MH-7861").sorted)
    assert(parkingLot.getRegNumbersByColor("Yellow").map(item => item.toList).right.get == List("GF-04-TR-7754"))

  }

  test("reg number should not be returned on empty parking lot") {
    val parkingLot = new ParkingLot(7)
    assert(parkingLot.getRegNumbersByColor("Yellow") == Left("Not found"))

  }

  test("reg number should not be returned if exceeded capacity") {
    val parkingLot = new ParkingLot(6)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))
    parkingLot.parkVehicle(Vehicle("GF-04-TR-7754", "Yellow"))

    assert(parkingLot.getRegNumbersByColor("Yellow") == Left("Not found"))

  }

  test("slots should be returned properly") {
    val parkingLot = new ParkingLot(6)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))

    assert(parkingLot.getSlotsByColor("Blue").right.get.toList.sorted == List(2, 6).sorted)

  }

  test("slots should not be returned if exceeded capacity") {
    val parkingLot = new ParkingLot(6)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Yellow"))

    assert(parkingLot.getSlotsByColor("Yellow") == Left("Not found"))

  }

  test("slots by reg number should be returned properly") {
    val parkingLot = new ParkingLot(6)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))

    assert(parkingLot.getSlotByRegNumber("AK-03-MH-5566").right.get == 2)

  }

  test("slots by reg number should not be returned") {
    val parkingLot = new ParkingLot(6)

    parkingLot.parkVehicle(Vehicle("TT-04-AH-6788", "Red"))
    parkingLot.parkVehicle(Vehicle("AK-03-MH-5566", "Blue"))
    parkingLot.parkVehicle(Vehicle("KA-01-TH-4321", "Green"))
    parkingLot.parkVehicle(Vehicle("KA-02-MH-5521", "Green"))
    parkingLot.parkVehicle(Vehicle("AM-02-MH-5521", "Red"))
    parkingLot.parkVehicle(Vehicle("DF-77-MH-7861", "Blue"))
    parkingLot.parkVehicle(Vehicle("AS-77-HG-6543", "Blue"))
    parkingLot.parkVehicle(Vehicle("TH-65-MM-7861", "Yellow"))


    assert(parkingLot.getSlotByRegNumber("TH-65-MM-7861") == Left("Not found"))

  }

}
