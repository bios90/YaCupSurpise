package com.bios.yacupsurpise.networking.responses

import com.google.gson.annotations.SerializedName
import okhttp3.Headers
import java.io.Serializable

open class BaseResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("errors") var errors: List<String>? = null,
) : Serializable

fun BaseResponse?.hasErrors(): Boolean {
    if (this == null) {
        return false
    }

    if (this.errors == null) {
        return false
    }

    if (this.errors!!.size == 0) {
        return false
    }

    return true
}

fun BaseResponse?.isSuccessful(): Boolean {
    if (this == null) {
        return false
    }

    if (this.status == null) {
        return false
    }

    if (this.status.equals("success", true)) {
        return true
    }

    return false
}

fun BaseResponse?.isFailed(): Boolean {
    if (this == null) {
        return false
    }

    if (this.status == null) {
        return false
    }

    if (this.status.equals("failed", true)) {
        return true
    }

    return false
}

fun BaseResponse?.isError(): Boolean {
    if (this == null) {
        return false
    }

    if (this.status == null) {
        return false
    }

    if (this.status.equals("error", true)) {
        return true
    }

    return false
}