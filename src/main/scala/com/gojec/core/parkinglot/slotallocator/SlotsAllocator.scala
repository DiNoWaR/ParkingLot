package com.gojec.core.parkinglot.slotallocator

import scala.collection.mutable.{Set => MSet}

/**
  * Object that allocate nearest free slot for vehicles
  *
  * @param maxCapacity is max capacity of parking lot
  */
class SlotsAllocator(maxCapacity: Int) {

  private val slotSet: MSet[Node] = initializeSlotSet()

  def acquireSlot(): Int = {
    val nearestSlot = slotSet.min
    nearestSlot.occupied = true
    nearestSlot.value
  }

  def releaseSlot(value: Int): Unit = {
    slotSet.find(item => item.value == value) match {
      case Some(slot) => slot.occupied = false
      case None => println("Not found")
    }
  }

  private def initializeSlotSet(): MSet[Node] = {

    val initializedSet = MSet[Node]()

    for (counter <- 1 to maxCapacity) {
      initializedSet += Node(counter, occupied = false)
    }

    initializedSet
  }

}


case class Node(value: Int, var occupied: Boolean) extends Ordered[Node] {

  def compare(that: Node): Int = {

    if (this.occupied && that.occupied | !this.occupied && !that.occupied) {
      this.value - that.value
    }
    else {
      if (this.occupied) {
        1
      }
      else {
        -1
      }
    }

  }

}