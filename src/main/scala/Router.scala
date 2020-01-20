import Service._
import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._

trait Router {
  def route: Route
}


class AppRouter(service: ActorRef) extends Router with Directives with ApiRequestParserDirectives with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = path("") {
    post {
      entity(as[ApiRequest]) { genericApiRequest =>
        parseApiRequest(genericApiRequest) { r => {
          validateWith(ApiRequestValidator)(r) {
            complete {
              implicit val timeout: Timeout = 5.seconds

              r match {
                case r: RealRootRequest => (service ? r).mapTo[RealRootResponse]
                case r: EchoRequest => (service ? r).mapTo[EchoResponse]
                case r: PingRequest => (StatusCodes.NoContent, (service ? r).mapTo[PingResponse])
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
