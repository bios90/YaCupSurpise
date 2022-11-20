package com.bios.yacupsurpise.data.models

import java.io.Serializable

interface ObjectWithImageUrl : Serializable {
    var imageUrl: String?

    companion object {
        fun getFromString(str: String): ObjectWithImageUrl =
            object : ObjectWithImageUrl {
                override var imageUrl: String? = str
            }
    }
}

interface ObjectWithVideoUrl {
    var videoUrl: String?
}
