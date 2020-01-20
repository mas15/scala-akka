trait Validator[T] {
  def validate(t: T): Option[ApiError]
}

object ApiRequestValidator extends Validator[ApiRequest] {

  def validate(apiRequest: ApiRequest): Option[ApiError] = {
    apiRequest match {
      case apiRequest: EchoRequest => validateEcho(apiRequest)
      case apiRequest: RealRootRequest => validateRealRoot(apiRequest)
      case _ => None
    }
  }

  def validateEcho(apiRequest: EchoRequest): Option[ApiError] =
    if (apiRequest.message.length > 50)
      Some(ApiError.tooLongMessage)
    else
      None

  def validateRealRoot(apiRequest: RealRootRequest): Option[ApiError] =
    if (apiRequest.index < 1 || apiRequest.radicand < 1)
      Some(ApiError.notPositiveValue)
    else
      None
}
