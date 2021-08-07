import akka.NotUsed
import akka.actor.{Actor, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.ThrottleMode
import akka.stream.alpakka.sse.scaladsl.EventSource
import akka.stream.alpakka.sse.scaladsl.EventSource.EventSource
import akka.stream.scaladsl.{Sink, Source}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt


class Connector extends Actor{

  implicit val system : ActorSystem = context.system;
  implicit val dispatcher = context.dispatcher
  val send : HttpRequest => Future[ HttpResponse ] = Http().singleRequest(_)

  val baseURL = "http://localhost:4000/"
  val routes : ListBuffer[String] = ListBuffer("tweets/1", "tweets/2")
  val eventSources: ListBuffer[Source[ ServerSentEvent, NotUsed ]] = ListBuffer()

  routes.foreach(route => {
    eventSources += EventSource(
        uri = Uri(baseURL + route),
        send,
        initialLastEventId = None,
        retryDelay = 1.second
      )


  })

  def receive : Receive = {
    case "start" => {
      start()
    }
    case _ => {println("Unexpected message")}
  }

def start(): Unit ={
  var allCompleted = false;
  while (true){
    val futures:ListBuffer[ Future[Seq[ServerSentEvent]]] = ListBuffer()
    eventSources.foreach(es =>{
      futures += es.throttle(1, 1.milliseconds, 1, ThrottleMode.Shaping).take(20).runWith(Sink.seq)
    })
    futures.foreach(future =>{
      future.foreach(se=> se.foreach(
        ev => {
          val event = ev.getData();
          println(event)
        }
      ))

    })
    allCompleted = false;
    while (!allCompleted){
      allCompleted = true;
      futures.foreach(future => {
        if(!future.isCompleted){
          allCompleted = future.isCompleted
        }
      })
    }
  }
}

}
