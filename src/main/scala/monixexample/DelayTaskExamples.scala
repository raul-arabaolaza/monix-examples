package monixexample

import monix.eval._
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.duration._

object DelayTaskExample extends App {
  // A simple task definition
  val simpleSuccess = Task[Int] {
    println("Executing simple successfull task")
    10
  }
  val simpleError = Task[Int] {
    println("Executing simple task with error")
    throw new Exception("This is a very informative error")
  }
  val simpleCallback = new Callback[Int] {
    def onSuccess(value:Int):Unit = {
      println("On success with value "+value)
    }
    def onError(ex:Throwable):Unit = {
      println("On error with value "+ex)
    }
  }

  // We can delay the execution of a task, delayExecution holds the execution
  // the specified amount of time
  println("Starting delayExecution example")
  def delayedExecution = simpleSuccess.delayExecution(1.seconds)
  delayedExecution.runAsync(simpleCallback)
  Thread.sleep(500)
  println("After waiting for half a second")
  // In contrast if we do not delay the task should execute before the sleep
  // finishes
  simpleSuccess.runAsync(simpleCallback)
  Thread.sleep(500)
  println("After waiting for half a second")

  // We can delay the notification of a task, delayResult holds the execution
  // the specified amount of time
  println("Starting delayResult example")
  def delayedResult = simpleSuccess.delayResult(2.seconds)
  delayedResult.runAsync(simpleCallback)
  Thread.sleep(500)
  println("After waiting for half a second")
  // delayResult does not delay the errors
  def delayedError = simpleError.delayResult(2.seconds)
  delayedError.runAsync(simpleCallback)
  Thread.sleep(500)
  println("After waiting for half a second")
}
