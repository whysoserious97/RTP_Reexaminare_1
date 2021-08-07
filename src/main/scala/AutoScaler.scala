import akka.actor.{Actor, ActorSelection, ActorSystem}

class AutoScaler extends Actor{
  var messages = 0;
  implicit val system : ActorSystem = context.system;
  var spawner : ActorSelection = system.actorSelection("user/Spawner")
  val t = new java.util.Timer()

  val task = new java.util.TimerTask {
    def run() = {
      spawner ! messages;
      messages = 0;
    }
  }

  t.schedule(task, 1000L, 1000L)
  override def receive : Receive = {
    case str : String => {
        messages +=1
    }
  }
}
