import math.pow
import Service.{EchoRequest, PingRequest, RealRootRequest}
import akka.actor.{Actor, ActorLogging, Props}


object Service {
  def props(): Props = Props(new Service())

  sealed trait ServiceMsg
  case class RealRootRequest(index: Int, radicand: Int) extends ServiceMsg
  case class EchoRequest(message: String) extends ServiceMsg
  case class PingRequest() extends ServiceMsg
}

class Service extends Actor with ActorLogging {
  def receive: Receive = {
    case PingRequest() =>
      log.info("Received ping request")
      sender() ! PingResponse()
    case RealRootRequest(index, radicand) =>
      log.info("Received root request")
      sender() ! RealRootResponse(realRoot=pow(radicand, 1.toDouble/index))
    case EchoRequest(message) =>
      log.info("Received echo request")
      sender() ! EchoResponse(message)
  }
}