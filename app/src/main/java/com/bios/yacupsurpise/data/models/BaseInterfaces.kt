package com.bios.yacupsurpise.data.models

import java.io.Serializable

interface ObjectWithId {
    val id: Long?
}

interface ObjectWithImageUrl : Serializable {
    val imageUrl: String?

    companion object {
        fun getFromString(str: String): ObjectWithImageUrl =
            object : ObjectWithImageUrl {
                override val imageUrl: String = str
            }
    }
}

interface ObjectWithVideoUrl {
    val videoUrl: String?
}
