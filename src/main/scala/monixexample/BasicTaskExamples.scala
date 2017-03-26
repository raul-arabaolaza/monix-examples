package monixexample

import monix.eval._
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.duration._

object BasicTaskExample extends App {
  // A simple task definition that will be executed asynchronously, this is
  // only evaluated when runAsync is called
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
  // This is just a way to make sure we can see the async behaviour without
  // forcing any delay, result is not deterministic but should probe async
  // behaviour
  for (i <- 1 to 10) {
    simpleSuccess.runAsync(simpleCallback)
    println("I am doing something just after invoking the async task")
    simpleError.runAsync(simpleCallback)
    println("I am doing something just after invoking the async task")
  }
}
