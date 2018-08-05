import java.text.NumberFormat
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.{CountDownLatch, CyclicBarrier, ExecutorService, Executors}

object Main extends App {

  private val TEST_COOL_OF_MS = 10
  private val vehicles: List[Vehicle] = List(new LockFreeVehicle())
  private val executor: ExecutorService = Executors.newCachedThreadPool()

  private val NUM_READER = 1 // args(0).toInt
  private val NUM_WRITER = 1 //args(1).toInt
  private val TEST_DURATION_MS = 1000 // args(2).toInt

  run


  def run: Unit = {

    0 to 5 foreach { _ => {

      vehicles foreach { vehicle => {

        System.gc()
        Thread.sleep(TEST_COOL_OF_MS)

        vRun(vehicle)

      }
      }

    }
    }

    executor.shutdown()
  }


  def vRun(vehicle: Vehicle): Unit = {

    val result = new Result
    val runningFlag = new AtomicBoolean(true)
    val latch = new CountDownLatch(NUM_WRITER + NUM_READER) // for waiting until all Threads (Readers ,Writers ) finishes work
    val barrier = new CyclicBarrier(NUM_WRITER + NUM_READER + 1) // for waiting all Threads (Readers ,Writers ) to start working together

    0 until  NUM_READER foreach (id => executor.execute(ReaderRunnable(id, result, vehicle, runningFlag, barrier, latch)))

    0 until  NUM_WRITER foreach (id => executor.execute(WriterRunnable(id, result, vehicle, runningFlag, barrier, latch)))

    awaitBarrier(barrier) // break barrier

    // wait Readers and Writers to do work in TEST_DURATION_MS then after that stop the work by setting the runningFlag to false
    Thread.sleep(TEST_DURATION_MS)

    runningFlag.set(false)

    latch.await() // waiting until all Threads cal latch.countDown()

    println(s"readers = $NUM_READER writers = $NUM_WRITER "+ vehicle.getClass.getSimpleName+" => "+result.toString)


  }

  def awaitBarrier(cyclicBarrier: CyclicBarrier): Unit = {
    try {
      cyclicBarrier.await()
    } catch {
      case e: Exception => print(e.getMessage)
    }
  }

  class Result {

    val readers: Array[Long] = new Array[Long](NUM_READER)
    val writers: Array[Long] = new Array[Long](NUM_WRITER)

    val readAttempts: Array[Long]  = new Array[Long](NUM_READER)
    val writeAttempts: Array[Long] = new Array[Long](NUM_WRITER)
    val moveHappened: Array[Long]  = new Array[Long](NUM_READER)

    override def toString: String = {

      val numFormat = NumberFormat.getInstance()

      val formatFn = ( num :Long) => numFormat format num

      val reads = numFormat.format(readers.sum)
      val writes = numFormat.format(writers.sum)
      val allReads = readers map formatFn  mkString("[", ",", "]")
      val allWrites = writers map formatFn mkString("[", ",", "]")
      val readAttemptsStr = readAttempts map formatFn mkString("[", ",", "]")
      val writeAttemptsStr = writeAttempts map formatFn mkString("[", ",", "]")
      val moveHappenedStr = moveHappened  map formatFn mkString("[", ",", "]")

      s"""  reads=$reads : $allReads
         | moves= $writes : $allWrites
         | readAttempts  = $readAttemptsStr
         | writeAttempts = $writeAttemptsStr
         | moveHappened  = $moveHappenedStr """.stripMargin.replaceAll("\n", " ")
    }

  }

  case class ReaderRunnable(
                             id: Int,
                             result: Result,
                             vehicle: Vehicle,
                             runningFlag: AtomicBoolean,
                             barrier: CyclicBarrier,
                             latch: CountDownLatch
                           ) extends Runnable {

    override def run(): Unit = {

      awaitBarrier(barrier) // wait to start all readers and writers at the same time

      val currentCoordinates: Array[Int] = Array(0, 0)
      val lastCoordinates: Array[Int] = Array(0, 0)

      var readCount = 0
      var readAttempts = 0
      var moveHappened = 0

      while (runningFlag.get()) {

        readAttempts += vehicle.readPosition(currentCoordinates)
        readCount += 1

        if (lastCoordinates(0) != currentCoordinates(0) && lastCoordinates(1) != currentCoordinates(1)) {
          moveHappened += 1
          lastCoordinates(0) = currentCoordinates(0)
          lastCoordinates(1) = currentCoordinates(1)

        }

      }

      latch.countDown()

      result.readers(id) = readCount
      result.moveHappened(id) = moveHappened
      result.readAttempts(id) = readAttempts


    }
  }

  case class WriterRunnable(
                             id: Int,
                             result: Result,
                             vehicle: Vehicle,
                             runningFlag: AtomicBoolean,
                             barrier: CyclicBarrier,
                             latch: CountDownLatch
                           ) extends Runnable {

    override def run(): Unit = {

      awaitBarrier(barrier)

      var writeCount = 0
      var writeAttempts = 0

      while (runningFlag.get()) {
        val moveAttempts = vehicle.move(1, 1)
        writeAttempts += moveAttempts
        writeCount += 1

      }

      result.writers(id) = writeCount
      result.writeAttempts(id) = writeAttempts

      latch.countDown()
    }
  }


}
