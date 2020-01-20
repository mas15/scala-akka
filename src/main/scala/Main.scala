import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load()
    val host = config.getString("http.host")
    val port = config.getInt("http.port")

    implicit val system: ActorSystem = ActorSystem(name = "system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val router = new AppRouter
    val server = new Server(router, host, port)

    val binding = server.bind()
    binding.onComplete {
      case Success(_) => println("Server started!")
      case Failure(error) => println(s"Failed: ${error.getMessage}")
    }

    import scala.concurrent.duration._
    Await.result(binding, 3.seconds)
  }
}
