package com.bios.yacupsurpise.utils

import android.opengl.GLSurfaceView
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.base.addLifeCycleObserver
import com.bios.yacupsurpise.utils.files.FileItem
import com.bios.yacupsurpise.utils.files.FileManager
import com.daasuu.camerarecorder.CameraRecorder
import com.daasuu.camerarecorder.CameraRecorderBuilder
import com.daasuu.camerarecorder.LensFacing
import java.io.File


class CameraRecordManager(
    private val act: BaseActivity,
    private val surfaceView: GLSurfaceView
) {
    private var cameraRecorder: CameraRecorder? = null
    private var isRecording: Boolean = false
    private var recordingFile: File? = null

    init {
        act.addLifeCycleObserver(
            onResume = {
                cameraRecorder = CameraRecorderBuilder(act, surfaceView)
                    .lensFacing(LensFacing.FRONT)
                    .build()
            },
            onPause = {
                isRecording = false
                recordingFile = null
                cameraRecorder?.stop()
                cameraRecorder?.release()
                cameraRecorder = null
            }
        )
    }

    fun startRecording() {
        if (isRecording) {
            return
        }
        val file = FileManager.createTempVideoFile()
        cameraRecorder?.start(file.absolutePath)
        recordingFile = file
    }

    fun stop(): FileItem? {
        cameraRecorder?.stop()
        return recordingFile?.let(FileItem::createFromFile)
    }

}
