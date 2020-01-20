import akka.http.scaladsl.model.{StatusCode, StatusCodes}

final case class ApiError private(statusCode: StatusCode, message: String)

object ApiError {
  private def apply(statusCode: StatusCode, message: String): ApiError = new ApiError(statusCode, message)

  val generic: ApiError = new ApiError(StatusCodes.InternalServerError, "Unknown error.")

  val notPositiveValue: ApiError = new ApiError(StatusCodes.BadRequest, "Value has to be greater than 0.")

  val tooLongMessage: ApiError = new ApiError(StatusCodes.BadRequest, "Message is too long")

}
