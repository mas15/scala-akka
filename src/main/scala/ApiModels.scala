case class GenericApiRequest(
                              kind: String,
                              message: Option[String],
                              index: Option[Int],
                              radicand: Option[Int]
                            )

sealed trait ApiRequest

case class RealRootRequest(index: Int, radicand: Int) extends ApiRequest
case class EchoRequest(message: String) extends ApiRequest
case class PingRequest() extends ApiRequest

sealed trait Response

case class RealRootResponse(realRoot: Double) extends Response
case class EchoResponse(message: String) extends Response
case class PingResponse() extends Response