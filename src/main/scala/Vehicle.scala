
/**
  * trait that represent Concurrent Vehicle movement using different algo
  */
trait Vehicle {

  /**
    * Read the position of the vehicle into coordinate
    *
    * @param coordinates
    * @return
    */
  def readPosition(coordinates: Array[Int]): Int


  /**
    * Move the vehicle by XDelta and YDelta
    * @param xDelta
    * @param yDelta
    * @return
    */
  def move(xDelta: Int, yDelta: Int): Int
}
