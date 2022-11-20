package com.bios.yacupsurpise.networking.exceptions

import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getStringApp
import com.bios.yacupsurpise.networking.responses.BaseResponse
import com.bios.yacupsurpise.networking.responses.hasErrors
import com.bios.yacupsurpise.networking.responses.isSuccessful

class NoInternetException : RuntimeException(getStringApp(R.string.err_not_internet))
class UnknownServerError : RuntimeException(getStringApp(R.string.err_loading))
class ParsingError : RuntimeException(getStringApp(R.string.err_parsing))
class ServerError(override val message: String) : RuntimeException()
class EmailOccupied() : RuntimeException()
class UserCanceled() : RuntimeException()
class MyTimeoutException : RuntimeException("***** Timeout ******")

fun BaseResponse.getError(): RuntimeException? {
    if (this.isSuccessful() == true) {
        return null
    }
    if (this.hasErrors()) {
        val message = this.errors?.joinToString("\n") ?: ""
        return ServerError(message)
    } else if (this.message.isNullOrEmpty().not()) {
        return ServerError(this.message ?: "")
    } else {
        return UnknownServerError()
    }
}

fun Throwable.toTextIfMyError(): String? {
    if (this is ServerError
        || this is NoInternetException
        || this is ParsingError
        || this is UnknownServerError
    ) {
        return this.message
    }

    return null
}