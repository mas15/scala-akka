import akka.http.scaladsl.server.{Directive1, Directives}

trait ApiRequestParserDirectives extends Directives {

  def parseApiRequest(apiRequest: GenericApiRequest): Directive1[ApiRequest] = provide(parseRequest(apiRequest))

  def parseRequest(apiRequest: GenericApiRequest): ApiRequest = {
    (apiRequest.kind, apiRequest.message, apiRequest.index, apiRequest.radicand) match {
      case ("realRoot", None, Some(i), Some(r)) => RealRootRequest(index = i, radicand = r)
      case ("echo", Some(msg), None, None) => EchoRequest(msg)
      case ("ping", None, None, None) => PingRequest()
    }
  }

}