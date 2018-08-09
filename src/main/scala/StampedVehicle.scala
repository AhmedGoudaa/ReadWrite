import java.util.concurrent.locks.StampedLock

class StampedVehicle extends Vehicle {

  private[this] val stampedLock = new StampedLock

  private[this] var x: Int = 0
  private[this] var y: Int = 0

  /**
    * Read the position of the vehicle into coordinate
    *
    * @param coordinates
    * @return
    */
  override def readPosition(coordinates: Array[Int]): Int = {

    var tries = 1

    var stamp = stampedLock.tryOptimisticRead()
    coordinates(0) = x
    coordinates(1) = y

    if (!stampedLock.validate(stamp)) {
      tries += 1

      stamp = stampedLock.readLock()

      try {
        coordinates(0) = x
        coordinates(1) = y

      } finally {
        stampedLock.unlock(stamp)
      }


    }
    // return
    tries
  }

  /**
    * Move the vehicle by XDelta and YDelta
    *
    * @param xDelta
    * @param yDelta
    * @return
    */
  override def move(xDelta: Int, yDelta: Int): Int = {
    val stamp = stampedLock.writeLock()

    try {
      x += xDelta
      y += yDelta
    }finally {
      stampedLock.unlock(stamp)
    }

    // return
    1

  }
}
