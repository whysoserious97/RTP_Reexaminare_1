import akka.actor.Actor

class Worker extends Actor{
  def receive:Receive = {
    case str:String => {
      println( "Worker recieved : " + str)
    }
  }
}

