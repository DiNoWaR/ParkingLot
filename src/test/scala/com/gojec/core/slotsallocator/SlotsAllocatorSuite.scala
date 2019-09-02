package com.gojec.core.slotsallocator

import com.gojec.core.parkinglot.slotallocator.SlotsAllocator
import org.scalatest.FunSuite

class SlotsAllocatorSuite extends FunSuite {

  test("nearest free slot should be 1") {
    val allocator = new SlotsAllocator(5)
    assert(allocator.head.value == 1)
  }

  test("nearest free slot should be 2") {
    val allocator = new SlotsAllocator(5)
    allocator.allocateSlot()
    assert(allocator.head.value == 2)
  }

  test("nearest free slot should be 4") {
    val allocator = new SlotsAllocator(6)
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.checkAndUpdateMinFreeSlot(4)
    assert(allocator.head.value == 4)
  }

  test("nearest free slot should be 1 because of leaving vehicle") {
    val allocator = new SlotsAllocator(6)
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.allocateSlot()
    allocator.checkAndUpdateMinFreeSlot(4)
    allocator.checkAndUpdateMinFreeSlot(1)
    assert(allocator.head.value == 1)
  }

}
