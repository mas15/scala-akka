import Service.{ServiceMsg, EchoRequest, PingRequest, RealRootRequest}
import akka.http.scaladsl.server.{Directive1, Directives}

trait ApiRequestParserDirectives extends Directives {

  def parseApiRequest(apiRequest: ApiRequest): Directive1[ServiceMsg] = parseRequest(apiRequest)

  def parseRequest(apiRequest: ApiRequest): Directive1[ServiceMsg] = {
    (apiRequest.kind, apiRequest.message, apiRequest.index, apiRequest.radicand) match {
      case ("realRoot", None, Some(i), Some(r)) => provide(RealRootRequest(index = i, radicand = r))
      case ("echo", Some(msg), None, None) => provide(EchoRequest(msg))
      case ("ping", None, None, None) => provide(PingRequest())
      case _ => complete(ApiError.badRequest.statusCode, ApiError.badRequest.message)
    }
  }

}