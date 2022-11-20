package com.bios.yacupsurpise.networking.responses

import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.data.models.ModelUser
import com.google.gson.annotations.SerializedName

data class RespSurpriseSingle(
    @SerializedName("data") var surprise: ModelSurprise? = null,
) : BaseResponse()
