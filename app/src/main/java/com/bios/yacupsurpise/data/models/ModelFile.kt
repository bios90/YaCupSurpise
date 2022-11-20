package com.bios.yacupsurpise.data.models

import com.bios.yacupsurpise.data.enums.TypeFile
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ModelFile(
    override val id: Long? = null,
    @SerializedName("created_at")
    val created: Date? = null,
    @SerializedName("updated_at")
    val updated: Date? = null,
    @SerializedName("deleted_at")
    val deleted: Date? = null,
    @SerializedName("file_name")
    val fileName: String? = null,
    @SerializedName("file_original_name")
    val fileOriginalName: String? = null,
    @SerializedName("file_size")
    val fileSize: Long? = null,
    @SerializedName("file_type")
    val fileType: TypeFile? = null,
    val url: String? = null,
    @SerializedName("preview_image")
    val previewImage: ModelFile? = null
) : Serializable, ObjectWithId, ObjectWithImageUrl, ObjectWithVideoUrl {
    override val imageUrl: String?
        get() = url.takeIf { fileType == TypeFile.IMAGE }

    override val videoUrl: String?
        get() = url.takeIf { fileType == TypeFile.VIDEO }

}
