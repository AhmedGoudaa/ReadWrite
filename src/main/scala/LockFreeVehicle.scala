import java.util.concurrent.atomic.AtomicReference

import scala.annotation.tailrec

case class Position(x: Int, y: Int) {
  def move(xDelta: Int, yDelta: Int): Position = Position(x + xDelta, y + yDelta)
}

class LockFreeVehicle extends Vehicle {

  private val position: AtomicReference[Position] = new AtomicReference[Position](Position(0, 0))

  /**
    * Read the position of the vehicle into coordinate
    *
    * @param coordinates
    * @return
    */
  override def readPosition(coordinates: Array[Int]): Int = {
    val currentPosition = position.get()
    coordinates.update(0, currentPosition.x)
    coordinates.update(1, currentPosition.y)
    // the num of read Attempts =1
    1

  }

  /**
//    * Move the vehicle by XDelta and YDelta
//    *
//    * @param xDelta
//    * @param yDelta
//    * @return
//    */
//  override def move(xDelta: Int, yDelta: Int): Int = {
//
//    var numOfTries = 1
//
//    var expectedPosition: Position = position.get()
//
//    while (!position.compareAndSet(expectedPosition, expectedPosition.move(1, 1))) {
//      numOfTries += 1
//      expectedPosition = position.get()
//
//    }
//
//    numOfTries
//  }

  /**
    * Move the vehicle by XDelta and YDelta
    *
    * @param xDelta
    * @param yDelta
    * @return
    */
  override def move(xDelta: Int, yDelta: Int): Int = {

    val expectedPosition: Position = position.get()

    @tailrec
    def mo(expectedPos: Position, tries: Int): Int = {

      if (!position.compareAndSet(expectedPosition, expectedPosition.move(1, 1))) // compareAndSet == XADD in jdk +8 and == CAS in jdk -8
      {
        mo(position.get(), tries + 1)
      } else {
        tries
      }
    }

    mo(expectedPosition, 1)

  }
}
