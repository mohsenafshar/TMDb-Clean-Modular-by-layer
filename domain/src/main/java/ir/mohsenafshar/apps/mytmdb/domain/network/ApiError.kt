package ir.mohsenafshar.apps.mytmdb.domain.network


sealed class ApiError {

    class Unknown(val throwable: Throwable) : ApiError()

    class BadResponseCode(val responseCode: Int) : ApiError()

    object Parse : ApiError()

    object NoConnection : ApiError()

    object RefreshTokenFailed : ApiError()

    object NoContent : ApiError()

    object Internal : ApiError()

    sealed class HandledError(val error: Error) : ApiError() {
        companion object {
            const val BAD_REQUEST = 400
            const val FORBIDEN = 403
            const val NOT_FOUND = 404
            const val REQUEST_TIMEOUT = 408
            const val CONFLICT = 409
            const val TOO_MANY_REQUESTS = 429
            const val VERIFY_OTP_FAILED = 1000
        }

        class VerifyOtpFailed(val message: String) : HandledError(Error(VERIFY_OTP_FAILED, message))
        class NotFound(val message: String) : HandledError(Error(NOT_FOUND, message))
        class Forbidden(val message: String) : HandledError(Error(FORBIDEN, message))
        class BadRequest(val message: String) : HandledError(Error(BAD_REQUEST, message))
        class TooManyRequests(val message: String) : HandledError(Error(TOO_MANY_REQUESTS, message))
        class Global(val globalError: Error) : HandledError(globalError)
    }

}


