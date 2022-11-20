package com.bios.yacupsurpise.networking

import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.networking.api.Api
import com.bios.yacupsurpise.networking.responses.BaseResponse
import com.bios.yacupsurpise.networking.responses.NetworkRequest
import com.bios.yacupsurpise.networking.responses.RespUserSingle
import com.bios.yacupsurpise.networking.responses.RespUsers
import kotlinx.coroutines.CoroutineScope

class BaseNetworker(
    private val scope: CoroutineScope,
    private val api: Api,
) {
    fun makeLogin(
        email: String,
        password: String,
        actionSuccess: (ModelUser?) -> Unit,
        actionError: (String) -> Unit,
    ) {
        request<RespUserSingle> {
            apiRequest {
                api.login(email = email, password = password)
            }
            onErrorMessage(actionError)
            onSuccess { actionSuccess.invoke(it?.user) }
        }
    }

    fun makeRegister(
        name: String,
        email: String,
        password: String,
        actionSuccess: (ModelUser?) -> Unit,
        actionError: (String) -> Unit,
    ) {
        request<RespUserSingle> {
            apiRequest {
                api.register(
                    name = name,
                    email = email,
                    password = password
                )
            }
            onErrorMessage(actionError)
            onSuccess { actionSuccess.invoke(it?.user) }
        }
    }

    fun getUsers(
        actionSuccess: (List<ModelUser>) -> Unit,
        actionError: (String) -> Unit,
    ) {
        request<RespUsers> {
            apiRequest { api.getAllUsers() }
            onErrorMessage(actionError)
            onSuccess { actionSuccess.invoke(it?.users ?: emptyList()) }
        }
    }

    private inline fun <reified T : BaseResponse> request(request: NetworkRequest<T>.() -> Unit) =
        NetworkRequest<T>()
            .apply(request)
            .apply {
                objClass(T::class.java)
                scope(scope)
            }
            .run()
}