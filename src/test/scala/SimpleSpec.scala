
import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestKit
import org.scalatest.{Matchers, WordSpecLike}

class SimpleSpec extends WordSpecLike with Matchers with ScalatestRouteTest {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val testPing: ApiRequest = ApiRequest(kind = "ping", message = None, index = None, radicand = None)
  val testEcho: ApiRequest = ApiRequest(kind = "echo", message = Some("halko"), index = None, radicand = None)
  val testRealRoot: ApiRequest = ApiRequest(kind = "realRoot", message = None, index = Some(2), radicand = Some(16))
  val testWrongReq: ApiRequest = ApiRequest(kind = "echo", message = None, index = None, radicand = None)

  val service: ActorRef = system.actorOf(Service.props(), "service")
  val router = new AppRouter(service)

  "This great api" should {

    "return the same message when echo" in {
      Post("/", testEcho) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val resp = responseAs[EchoResponse]
        resp.message shouldBe testEcho.message.get
      }
    }

    "return 204 when ping" in {
      Post("/", testPing) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "return 4 when sqrt of 16" in {
      Post("/", testRealRoot) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val resp = responseAs[RealRootResponse]
        resp.realRoot shouldBe 4.toDouble
      }
    }

    "return 400 when request data is not correct" in {
      Post("/", testWrongReq) ~> router.route ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }
  }
}
