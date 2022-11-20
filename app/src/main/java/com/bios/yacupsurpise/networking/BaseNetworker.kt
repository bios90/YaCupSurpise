package com.bios.yacupsurpise.networking

import android.os.FileObserver
import com.bios.yacupsurpise.R
import com.bios.yacupsurpise.base.getStringApp
import com.bios.yacupsurpise.data.enums.TypeFile
import com.bios.yacupsurpise.data.models.ModelFile
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.data.models.ModelUser
import com.bios.yacupsurpise.networking.api.Api
import com.bios.yacupsurpise.networking.exceptions.ParsingError
import com.bios.yacupsurpise.networking.exceptions.UnknownServerError
import com.bios.yacupsurpise.networking.responses.BaseResponse
import com.bios.yacupsurpise.networking.responses.NetworkRequest
import com.bios.yacupsurpise.networking.responses.RespFile
import com.bios.yacupsurpise.networking.responses.RespSurpriseSingle
import com.bios.yacupsurpise.networking.responses.RespSurprises
import com.bios.yacupsurpise.networking.responses.RespUserSingle
import com.bios.yacupsurpise.networking.responses.RespUsers
import com.bios.yacupsurpise.networking.responses.requestMultiple
import com.bios.yacupsurpise.utils.files.FileItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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

    fun getSendedSurprises(
        id: Long,
        onErrorAction: (String) -> Unit,
        onSuccessAction: (List<ModelSurprise>) -> Unit,
    ) {
        request<RespSurprises> {
            apiRequest { api.getMySended(id) }
            onErrorMessage(onErrorAction)
            onSuccess { onSuccessAction.invoke(it?.surprises ?: emptyList()) }
        }
    }

    fun getReceivedSurprises(
        id: Long,
        onErrorAction: (String) -> Unit,
        onSuccessAction: (List<ModelSurprise>) -> Unit,
    ) {
        request<RespSurprises> {
            apiRequest { api.getMyReceived(id) }
            onErrorMessage(onErrorAction)
            onSuccess { onSuccessAction.invoke(it?.surprises ?: emptyList()) }
        }
    }

    fun createSurprise(
        senderId: Long,
        receiverId: Long,
        text: String?,
        image: FileItem?,
        video: FileItem?,
        onError: (String) -> Unit,
        onSuccess: (ModelSurprise) -> Unit,
    ) {
        requestMultiple {
            scope(scope)
            onErrorMessage(onError)
            multipleRequests {
                val imageFileId = image?.let {
                    uploadFile(it)
                }?.id
                val videoFileId = video?.let {
                    uploadFile(it)
                }?.id

                val attachmentType = when {
                    imageFileId != null -> TypeFile.IMAGE
                    videoFileId != null -> TypeFile.VIDEO
                    else -> null
                }

                val surprise = api.createSurprise(
                    senderId = senderId,
                    receiverId = receiverId,
                    text = text,
                    fileType = attachmentType,
                    attachmentId = imageFileId ?: videoFileId
                ).toObjOrThrow(RespSurpriseSingle::class.java)
                    .surprise

                withContext(Dispatchers.Main) {
                    if (surprise != null) {
                        onSuccess(surprise)
                    } else {
                        onError(getStringApp(R.string.err_loading))
                    }
                }
            }
        }
    }

    fun sendReaction(
        surpriseId: Long,
        reaction: FileItem,
        onError: (String) -> Unit,
        onSuccess: (ModelSurprise) -> Unit
    ) {
        requestMultiple {
            scope(scope)
            onErrorMessage(onError)
            multipleRequests {

                val fileId = uploadFile(reaction)?.id ?: throw UnknownServerError()
                val surpriseUpdated = api.updateReaction(
                    surpriseId = surpriseId,
                    reactionId = fileId
                ).toObjOrThrow(RespSurpriseSingle::class.java)
                    .surprise ?: throw ParsingError()

                withContext(Dispatchers.Main) {
                    onSuccess(surpriseUpdated)
                }
            }
        }
    }

    fun rejectSurprise(
        surpriseId: Long,
        onError: (String) -> Unit,
        onSuccess: (ModelSurprise) -> Unit
    ) {
        request<RespSurpriseSingle> {
            apiRequest { api.rejectSurprise(surpriseId) }
            onErrorMessage(onError)
            parseChecker { it?.surprise != null }
            onSuccess { onSuccess.invoke(requireNotNull(it?.surprise)) }
        }
    }

    suspend fun uploadFile(fileItem: FileItem): ModelFile? {
        val partFile = fileItem.toMultiPartData("file")
        return if (fileItem.isImage()) {
            api.uploadImage(partFile)
        } else {
            api.uploadVideo(partFile)
        }
            .toObjOrThrow(RespFile::class.java)
            .file
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
