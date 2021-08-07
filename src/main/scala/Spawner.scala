import akka.actor.{Actor, OneForOneStrategy}
import akka.actor.SupervisorStrategy.Restart

import scala.concurrent.duration.DurationInt

class Spawner extends Actor{
  override val supervisorStrategy : OneForOneStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute){
    case _: Exception     => Restart
  }

  override def receive : Receive = ???
}
