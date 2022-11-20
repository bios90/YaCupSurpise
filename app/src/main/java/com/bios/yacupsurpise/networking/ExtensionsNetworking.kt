package com.bios.yacupsurpise.networking

import com.bios.yacupsurpise.base.gsonGlobal
import com.bios.yacupsurpise.networking.exceptions.ParsingError
import com.bios.yacupsurpise.networking.exceptions.UnknownServerError
import com.bios.yacupsurpise.networking.exceptions.getError
import com.bios.yacupsurpise.networking.responses.BaseResponse
import com.bios.yacupsurpise.networking.responses.isError
import com.bios.yacupsurpise.networking.responses.isFailed
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Response

fun <T> Response<ResponseBody>.toObjOrThrow(clazz: Class<T>): T {
    val response_as_str = this.getBodyAsStr()

    if (response_as_str == null) {
        throw UnknownServerError()
    }

    val base_response = response_as_str.toObjOrNullGson(BaseResponse::class.java)

    if (base_response == null) {
        throw ParsingError()
    }

    if (base_response.isFailed() || base_response.isError()) {
        val error = base_response.getError()
        if (error != null) {
            throw error
        }
    }

    val obj = response_as_str.toObjOrNullGson(clazz)

    if (obj == null) {
        throw ParsingError()
    }

    return obj
}

fun Response<ResponseBody>?.getBodyAsStr(): String? {
    if (this?.code() == 200) {
        return this.body()?.string()
    }

    return this?.errorBody()?.string()
}

fun <T> String?.toObjOrNullGson(obj_class: Class<T>): T? {
    val gson = gsonGlobal
    try {
        if (this?.equals("null") == true || this?.equals("\"null\"") == true) {
            return null
        }

        return gson.fromJson(this, obj_class)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
