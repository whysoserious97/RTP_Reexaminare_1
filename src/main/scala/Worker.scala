import akka.actor.Actor

import scala.Console.{CYAN, RESET}

class Worker extends Actor{
  def receive:Receive = {
    case str:String => {
      println( "Worker recieved : " + str)
    }
  }

  override def postRestart(reason:Throwable){    // overriding preStart method
    println("\nI am restarted and reason is "+reason.getMessage + "\n")
  }
}

