case class GenericApiRequest(kind: String, message: Option[String], index: Option[Int], radicand: Option[Int])

sealed trait ApiRequest
case class RealRootRequest(index: Int, radicand: Int) extends ApiRequest
case class EchoRequest(message: String) extends ApiRequest
case class PingRequest() extends ApiRequest

