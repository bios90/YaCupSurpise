package com.bios.yacupsurpise.data.models

import com.bios.yacupsurpise.data.enums.TypeFile
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class ModelSurprise(
    override val id: Long? = null,
    @SerializedName("sender_id")
    val senderId: Long? = null,
    @SerializedName("receiver_id")
    val receiverId: Long? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("file_type")
    val fileType: TypeFile? = null,
    @SerializedName("attachment")
    val attachment: ModelFile? = null,
    @SerializedName("reaction")
    val reaction: ModelFile? = null,
    @SerializedName("is_rejected")
    val isRejected: Boolean? = null,
    @SerializedName("created_at")
    val created: Date? = null,
    @SerializedName("reaction_date")
    val reactionDate: Date? = null,
    @SerializedName("sender")
    val sender: ModelUser? = null,
    @SerializedName("receiver")
    val receiver: ModelUser? = null,
) : Serializable, ObjectWithId, ObjectWithVideoUrl, ObjectWithImageUrl {

    override val videoUrl: String?
        get() = attachment?.url

    override val imageUrl: String?
        get() = attachment?.url

    fun hasVideo() = attachment?.url != null && fileType == TypeFile.VIDEO
    fun hasImage() = attachment?.url != null && fileType == TypeFile.IMAGE
}
