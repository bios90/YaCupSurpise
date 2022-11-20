package com.bios.yacupsurpise.data.models

import com.bios.yacupsurpise.utils.files.FileItem

data class ModelReactionResult(
    val surprise: ModelSurprise,
    val reaction: FileItem
) : java.io.Serializable
