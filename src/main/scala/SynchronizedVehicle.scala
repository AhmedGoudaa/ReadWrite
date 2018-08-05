class SynchronizedVehicle extends Vehicle {

  private var x = 0
  private var y = 0
  /**
    * Read the position of the vehicle into coordinate
    *
    * @param coordinates
    * @return
    */
  override def readPosition(coordinates: Array[Int]): Int =  synchronized {
    coordinates(0)=x
    coordinates(1)=y
    1
  }

  /**
    * Move the vehicle by XDelta and YDelta
    *
    * @param xDelta
    * @param yDelta
    * @return
    */
  override def move(xDelta: Int, yDelta: Int): Int = synchronized {
    x += xDelta
    y += yDelta
    1
  }
}
