package com.bios.yacupsurpise.networking.responses

import android.util.Log
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getStringApp
import com.bios.yacupsurpise.base.isNetworkAvailable
import com.bios.yacupsurpise.networking.exceptions.NoInternetException
import com.bios.yacupsurpise.networking.exceptions.ParsingError
import com.bios.yacupsurpise.networking.exceptions.toTextIfMyError
import com.bios.yacupsurpise.networking.toObjOrThrow
import com.photogramma.logic.utils.ValidationData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class NetworkRequest<T> {
    private var apiRequest: (suspend () -> Response<ResponseBody>)? = null
    private var multipleRequestsAction: (suspend CoroutineScope.() -> Unit)? = null
    private var objClass: Class<T>? = null
    private var scope: CoroutineScope? = null
    private var onSuccess: ((T?) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onErrorMessage: ((String) -> Unit)? = null
    private var actionParseChecker: ((T?) -> Boolean)? = null
    private var exceptionHandler = CoroutineExceptionHandler { context, error ->
        scope?.launch(Dispatchers.Main) {
            onError?.invoke(error)
        }
        onErrorMessage?.let {
            val message = error.toTextIfMyError() ?: getStringApp(R.string.err_loading)
            scope?.launch(Dispatchers.Main) {
                it.invoke(message)
            }

        }
    }

    fun apiRequest(apiRequest: suspend () -> Response<ResponseBody>) = apply {
        this.apiRequest = apiRequest
    }

    fun multipleRequests(multipleRequests: suspend CoroutineScope.() -> Unit) = apply {
        this.multipleRequestsAction = multipleRequests
    }

    fun objClass(objClass: Class<T>) = apply {
        this.objClass = objClass
    }

    fun scope(scope: CoroutineScope) = apply {
        this.scope = scope
    }

    fun onError(onError: (Throwable) -> Unit) = apply {
        this.onError = onError
    }

    fun onErrorMessage(onErrorMessage: (String) -> Unit) = apply {
        this.onErrorMessage = onErrorMessage
    }

    fun onSuccess(onSuccess: (T?) -> Unit) = apply {
        this.onSuccess = onSuccess
    }

    fun parseChecker(parseChecker: (T?) -> Boolean) = apply {
        this.actionParseChecker = parseChecker
    }

    fun run() {
        if (multipleRequestsAction != null) {
            runAsMultiple()
        } else {
            runAsSingle()
        }
    }

    fun runAsMultiple() {
        val actionMultiple = requireNotNull(multipleRequestsAction, { "Error no action setted" })
        val scope = requireNotNull(scope, { "Error scope not setted" })

        scope.launch(
            context = Dispatchers.IO + exceptionHandler,
            block = actionMultiple
        )
    }

    fun runAsSingle() {
        val apiRequest = requireNotNull(apiRequest, { "Error apiRequest not setted" })
        val objClass = requireNotNull(objClass, { "Error objClass not setted" })
        val scope = requireNotNull(scope, { "Error scope not setted" })
        val actionSuccess = requireNotNull(onSuccess, { "Error action onSuccess not setted" })
        scope.launch(exceptionHandler) {
            if (isNetworkAvailable().not()) {
                throw NoInternetException()
            }

            launch(
                context = Dispatchers.IO + exceptionHandler,
                block = {
                    val obj = scope.async(Dispatchers.IO + exceptionHandler, block =
                    {
                        apiRequest().toObjOrThrow(objClass)
                    }).await()

                    actionParseChecker?.let { checker ->
                        if (checker.invoke(obj).not()) {
                            throw ParsingError()
                        }
                    }

                    withContext(Dispatchers.Main) {
                        actionSuccess.invoke(obj)
                    }
                }
            )
        }
    }
}

inline fun <reified T : BaseResponse> request(request: NetworkRequest<T>.() -> Unit) =
    NetworkRequest<T>()
        .apply { objClass(T::class.java) }
        .apply(request)
        .run()

fun requestMultiple(request: NetworkRequest<Any>.() -> Unit) =
    NetworkRequest<Any>().apply(request)
        .run()