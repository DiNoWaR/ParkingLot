package com.gojec.core.parkinglot.command

object Commands {

  sealed trait Command {
    val message: String
  }

  case object Park extends Command {
    val message = "park"
  }

  case object Leave extends Command {
    val message = "leave"
  }

  case object Status extends Command {
    val message = "status"
  }

  case object RegNumbersByColor extends Command {
    val message = "registration_numbers_for_cars_with_colour"
  }

  case object SlotsByColor extends Command {
    val message = "slot_numbers_for_cars_with_colour"
  }

  case object SlotByRegNumber extends Command {
    val message = "slot_number_for_registration_number"
  }

  case object Exit extends Command {
    val message = "exit"
  }

}
