package com.bios.yacupsurpise.networking.responses

import com.bios.yacupsurpise.data.models.ModelUser
import com.google.gson.annotations.SerializedName

data class RespUserSingle(
    @SerializedName("data") var user: ModelUser? = null,
) : BaseResponse()