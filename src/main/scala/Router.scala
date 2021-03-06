import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}


class AppRouter(service: Service) extends Router with Directives with ApiRequestParserDirectives with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = path("") {
    post {
      entity(as[GenericApiRequest]) { genericApiRequest =>
        parseApiRequest(genericApiRequest) { r => {
          validateWith(ApiRequestValidator)(r) {
            complete {
              r match {
                case r: RealRootRequest => RealRootResponse(service.realRoot(r.index, r.radicand))
                case r: EchoRequest => EchoResponse(service.echo(r.message))
                case r: PingRequest => (StatusCodes.NoContent, "")
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
