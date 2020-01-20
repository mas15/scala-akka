import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiResponse private(statusCode: StatusCode, message: String)

object ApiResponse {
  private def apply(statusCode: StatusCode, message: String): ApiResponse = new ApiResponse(statusCode, message)

  val contentNotFound: ApiResponse = new ApiResponse(StatusCodes.NoContent, "Content not found")
}
