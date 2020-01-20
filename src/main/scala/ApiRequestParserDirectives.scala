import akka.http.scaladsl.server.{Directive1, Directives}

trait ApiRequestParserDirectives extends Directives {

  def parseApiRequest(apiRequest: GenericApiRequest): Directive1[ApiRequest] = parseRequest(apiRequest)

  def parseRequest(apiRequest: GenericApiRequest): Directive1[ApiRequest] = {
    (apiRequest.kind, apiRequest.message, apiRequest.index, apiRequest.radicand) match {
      case ("realRoot", None, Some(i), Some(r)) => provide(RealRootRequest(index = i, radicand = r))
      case ("echo", Some(msg), None, None) => provide(EchoRequest(msg))
      case ("ping", None, None, None) => provide(PingRequest())
      case _ => complete(ApiError.badRequest.statusCode, ApiError.badRequest.message)
    }
  }

}