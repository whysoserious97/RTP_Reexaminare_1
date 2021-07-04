import akka.actor.Actor

class Router extends Actor{

  def receive:Receive = {
    case str:String => {
      println("Router received:" + str)
    }
  }
}
