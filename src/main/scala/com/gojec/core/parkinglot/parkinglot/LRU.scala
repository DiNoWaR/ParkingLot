package com.gojec.core.parkinglot.parkinglot

import scala.collection.mutable.{Map => MMap}

/**
  *
  * @param size
  */
class LRU(size: Int) {

  /**
    * Head of double linked list
    */
  var head: Node = initializeSlots()

  /**
    * Last element in double linked list
    */
  private var last: Node = _

  /**
    *
    */
  private val slotsNumberMap: Map[Int, Node] = initializeMap()


  def getMinFreeSlot(): Int = {
    val minFreeSlot = head.value
    placeSlotToLast()
    minFreeSlot
  }

  def checkAndUpdateMinFreeSlot(slot: Int) = {
    checkAndPlaceSlotToHead(slot)
  }

  private def checkAndPlaceSlotToHead(slot: Int): Unit = {

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

  private def initializeSlots(): Node = {

    val head = Node(1, null, null)
    var cursor = head


    var counter = 2

    while (counter <= size) {
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

  private def placeSlotToLast(): Unit = {
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

  private def initializeMap(): Map[Int, Node] = {

    val resultMap = MMap[Int, Node]()

    var currentNode = head

    for (count <- 1 until size) {
      resultMap += (count -> currentNode)
      currentNode = currentNode.next
    }

    resultMap.toMap
  }
}

case class Node(value: Int, var next: Node, var previous: Node)





















