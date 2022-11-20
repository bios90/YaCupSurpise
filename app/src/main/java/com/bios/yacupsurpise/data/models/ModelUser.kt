package com.bios.yacupsurpise.data.models

import java.io.Serializable

data class ModelUser(
    override val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val avatar: ModelFile? = null
) : Serializable, ObjectWithId
