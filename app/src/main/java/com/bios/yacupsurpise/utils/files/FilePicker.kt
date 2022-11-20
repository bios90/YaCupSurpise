package com.bios.yacupsurpise.utils.files

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bios.yacupsurpise.base.BaseActivity
import com.bios.yacupsurpise.ui.dialogs.DialogMediaSelect
import com.github.florent37.inlineactivityresult.kotlin.coroutines.startForResult
import com.github.florent37.inlineactivityresult.kotlin.startForResult
import com.photogramma.logic.utils.ValidationData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun pick(action: FilePicker.() -> Unit) = FilePicker().apply(action).apply { pick() }

class FilePicker {
    private var onImageSelected: ((FileItem) -> Unit)? = null
    private var onVideoSelected: ((FileItem) -> Unit)? = null
    private var onError: (() -> Unit)? = null
    private var act: BaseActivity? = null

    fun onImage(onImage: (FileItem) -> Unit) = apply {
        this.onImageSelected = onImage
    }

    fun onVideo(onImage: (FileItem) -> Unit) = apply {
        this.onVideoSelected = onImage
    }

    fun onError(onError: () -> Unit) = apply {
        this.onError = onError
    }

    fun setAct(act: BaseActivity) = apply {
        this.act = act
    }

    fun pick() {
        val act = requireNotNull(act)
        val dialog = DialogMediaSelect(
            onImageClicked = {
                pickImage(
                    activity = act,
                    action = { onImageSelected?.invoke(it) },
                    onError = { onError?.invoke() }
                )
            },
            onVideoClicked = {
                pickVideo(
                    activity = act,
                    action = { onVideoSelected?.invoke(it) },
                    onError = { onError?.invoke() }
                )
            }
        )
        dialog.show(act.supportFragmentManager, null)
    }
}

private fun pickImage(activity: BaseActivity, action: ((FileItem) -> Unit), onError: () -> Unit) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    activity.startForResult(intent) { result ->
        val data = result.data ?: return@startForResult
        resultDataToMyFileItem(
            activity = activity,
            data = data,
            onError = onError,
            onSuccess = action
        )
    }
}

private fun pickVideo(activity: BaseActivity, action: ((FileItem) -> Unit), onError: () -> Unit) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "video/*"
    activity.startForResult(intent) { result ->
        val data = result.data ?: return@startForResult
        resultDataToMyFileItem(
            activity = activity,
            data = data,
            onError = onError,
            onSuccess = action
        )
    }
}

private fun resultDataToMyFileItem(
    activity: BaseActivity,
    data: Intent,
    onSuccess: (FileItem) -> Unit,
    onError: () -> Unit
) {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError.invoke()
    }
    activity.lifecycleScope.launch(
        context = Dispatchers.IO + exceptionHandler,
        block = {
            val fileItem = FileItem.createFromData(data)
            withContext(Dispatchers.Main + exceptionHandler) {
                onSuccess.invoke(fileItem)
            }
        }
    )
}


