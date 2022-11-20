package com.bios.yacupsurpise.di

import com.bios.yacupsurpise.screens.act_reaction.ActReaction
import com.bios.yacupsurpise.utils.CameraRecordManager

class ActReactionInjector {
    private var cameraRecordManager: CameraRecordManager? = null

    fun provideCameraRecordManager(act: ActReaction) =
        cameraRecordManager ?: CameraRecordManager(
            act = act,
            surfaceView = act.provideSurfaceView()
        )?.also {
            this.cameraRecordManager = it
        }
}
