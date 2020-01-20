sealed trait Response
case class RealRootResponse(realRoot: Double) extends Response
case class EchoResponse(message: String) extends Response
case class PingResponse() extends Response