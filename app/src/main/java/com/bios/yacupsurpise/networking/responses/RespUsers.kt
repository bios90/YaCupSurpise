package com.bios.yacupsurpise.networking.responses

import com.bios.yacupsurpise.data.models.ModelUser
import com.google.gson.annotations.SerializedName

data class RespUsers(
    @SerializedName("data") var users: List<ModelUser>? = null,
) : BaseResponse()