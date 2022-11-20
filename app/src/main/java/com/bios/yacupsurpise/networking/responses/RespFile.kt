package com.bios.yacupsurpise.networking.responses

import com.bios.yacupsurpise.data.models.ModelFile
import com.bios.yacupsurpise.data.models.ModelUser
import com.google.gson.annotations.SerializedName

data class RespFile(
    @SerializedName("data") var file: ModelFile? = null,
) : BaseResponse()
