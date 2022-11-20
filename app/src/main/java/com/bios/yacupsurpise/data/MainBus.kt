package com.bios.yacupsurpise.data

import com.bios.yacupsurpise.data.models.ModelReactionResult
import com.bios.yacupsurpise.data.models.ModelSurprise
import com.bios.yacupsurpise.utils.files.FileItem
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

object MainBus {
    val flowSurpriseCreated = MutableSharedFlow<ModelSurprise>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    val flowReactionMade = MutableSharedFlow<ModelReactionResult>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    val flowSurpriseUpdated = MutableSharedFlow<ModelSurprise>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
}
