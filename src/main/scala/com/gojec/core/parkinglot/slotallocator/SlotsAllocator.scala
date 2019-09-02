package com.gojec.core.parkinglot.slotallocator

import scala.collection.mutable.{Map => MMap}

/**
  * Object that allocate nearest free slot for vehicle by using hashMap and double linked list
  * All operations work for O(1) complexity
  *
  * @param maxCapacity is max capacity of parking lot
  */
class SlotsAllocator(maxCapacity: Int) {

  /**
    * Head of double linked list
    */
  var head: Node = initializeSlots()

  /**
    * Last element in double linked list
    */
  private var last: Node = _


  private val slotsNumberMap: Map[Int, Node] = initializeMap()

  /**
    * Allocate slot for vehicle
    *
    * @return number of slots
    */
  def allocateSlot(): Int = {
    val minFreeSlot = head.value
    placeSlotNodeToLast()
    minFreeSlot
  }

  def checkAndUpdateMinFreeSlot(slot: Int) = {
    checkAndPlaceSlotNodeToHead(slot)
  }

  /**
    * Moves Node to head of list, when vehicle left parking if number of slot is less then head
    *
    * @param slot is number of slot
    */
  private def checkAndPlaceSlotNodeToHead(slot: Int): Unit = {

    if (slot < head.value | head.value == -1) {

      val newHead = slotsNumberMap(slot)

      if (newHead != head && newHead != last) {
        val previous = newHead.previous
        val next = newHead.next

        previous.next = next
        next.previous = previous

        newHead.next = head
        newHead.previous = null
        head = newHead
      }

      if (newHead eq last) {
        val previous = newHead.previous
        last = previous
        previous.next = null
        newHead.previous = null
        newHead.next = head
        head.previous = newHead
        head = newHead
      }
    }
  }

  /**
    * Moves Node to last of list, when vehicle parks
    *
    */
  private def placeSlotNodeToLast(): Unit = {
    if (head.next != null) {
      val newHead = head.next
      newHead.previous = null
      head.next = null
      head.previous = last
      last.next = head
      last = head
      head = newHead
    }
  }

  private def initializeSlots(): Node = {

    val head = Node(1, null, null)
    var cursor = head


    var counter = 2

    while (counter <= maxCapacity) {
      val current = Node(counter, null, null)
      cursor.next = current
      current.previous = cursor
      cursor = current
      counter += 1
    }
    val dummyNode = Node(-1, null, null)
    cursor.next = dummyNode
    dummyNode.previous = cursor
    last = dummyNode
    head
  }

  private def initializeMap(): Map[Int, Node] = {

    val resultMap = MMap[Int, Node]()

    var currentNode = head

    for (count <- 1 until maxCapacity) {
      resultMap += (count -> currentNode)
      currentNode = currentNode.next
    }

    resultMap.toMap
  }
}

case class Node(value: Int, var next: Node, var previous: Node)





















