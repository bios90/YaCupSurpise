package com.bios.yacupsurpise.utils.files

import android.net.Uri
import java.io.File
import android.content.Intent
import com.bios.yacupsurpise.base.Consts
import com.bios.yacupsurpise.base.uriToPartBody
import com.bios.yacupsurpise.data.models.ObjectWithImageUrl
import com.bios.yacupsurpise.data.models.ObjectWithVideoUrl
import okhttp3.MultipartBody
import java.io.Serializable
import java.lang.RuntimeException


class FileItem(private var uri: String) : Serializable, ObjectWithImageUrl, ObjectWithVideoUrl {
    override var imageUrl: String? = uri
    override var videoUrl: String? = uri

    companion object {
        fun createFromData(data: Intent): FileItem {
            val file: File
            val resultData = data.data ?: throw RuntimeException("**** Error null data passed ****")
            if (FileManager.isContentImage(resultData)) {
                val extension = FileManager.getExtensionFromContentUri(resultData)
                file = FileManager.createTempImageFile(extension ?: "")
            } else {
                val extension =
                    FileManager.getExtensionFromContentUri(data.data!!) ?: Consts.EXTENSION_MP4
                file = FileManager.createTempVideFile(extension)
            }

            FileManager.copyToFileFromIntentData(file, data)
            val myFile = FileItem(file.absolutePath)

            return myFile
        }

        fun createFromFile(file: File): FileItem {
            return FileItem(file.absolutePath)
        }
    }

    fun getUri(): Uri {
        return Uri.parse(uri)
    }

    fun getUriAsString(): String {
        return uri
    }

    fun getFile(): File {
        return File(getUri().path)
    }

    fun isImage(): Boolean {
        return FileManager.isFileImage(uri)
    }

    fun isVideo(): Boolean {
        return FileManager.isFileVideo(uri)
    }

    fun toMultiPartData(field_name: String): MultipartBody.Part? {
        return uriToPartBody(uri, field_name)
    }

    fun getSize(): Long {
        return getFile().length()
    }


    fun getSizeInMb(): Double {
        return getFile().length().toDouble() / (1024 * 1024).toDouble()
    }
}


