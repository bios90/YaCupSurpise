package com.bios.yacupsurpise.utils

import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.utils.files.FileItem
import com.bios.yacupsurpise.utils.files.pick

class MediaPickerManager(private val act: BaseActivity) {

    fun pickMedia(
        onImagePicked: (FileItem) -> Unit,
        onVideoPicked: (FileItem) -> Unit,
        onError: () -> Unit,
    ) {
        pick {
            setAct(act)
            onImage(onImagePicked)
            onVideo(onVideoPicked)
            onError(onError)
        }
    }
}
