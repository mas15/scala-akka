import akka.http.scaladsl.server.{Directives, Route}
import Service._

trait Router {
  def route: Route
}


class AppRouter() extends Router with Directives with ApiRequestParserDirectives with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = path("") {
    post {
      entity(as[GenericApiRequest]) { genericApiRequest =>
        parseApiRequest(genericApiRequest) { r => {
          validateWith(ApiRequestValidator)(r) {
            complete {
              r match {
                case r: RealRootRequest => RealRootResponse(
                  realRoot = handleRealRoot(r.index, r.radicand)
                )
                case r: EchoRequest => EchoResponse(
                  message = handleEcho(r.message)
                )
                case r: PingRequest => PingResponse()
                case _ => new NotImplementedError()
              }
            }
          }
        }
        }
      }
    }
  }
}
