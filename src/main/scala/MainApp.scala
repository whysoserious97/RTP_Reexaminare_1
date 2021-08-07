
import akka.actor.{ActorRef, ActorSystem, Props}

object MainApp {

  def main(args : Array[ String ]) : Unit = {
    val actorSystem:ActorSystem=ActorSystem("ActorSystem")
    val spawner = actorSystem.actorOf(Props[Spawner],"Spawner")
    val Con=actorSystem.actorOf(Props[Connector],"C")
    val router = actorSystem.actorOf(Props[Router],"R")
    val worker = actorSystem.actorOf(Props[Worker],"W")


    router ! "Hello Router"
    worker ! "Hello Worker"
    Con!"start"
  }
}
