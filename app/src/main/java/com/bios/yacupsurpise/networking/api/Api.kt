package com.bios.yacupsurpise.networking.api

import com.bios.yacupsurpise.data.enums.TypeFile
import com.bios.yacupsurpise.data.models.ModelSurprise
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface Api {

    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<ResponseBody>

    @GET("/get_users")
    suspend fun getAllUsers(): Response<ResponseBody>

    @GET("/get_my_sended")
    suspend fun getMySended(
        @Query("sender_id") id: Long
    ): Response<ResponseBody>

    @GET("/get_my_received")
    suspend fun getMyReceived(
        @Query("receiver_id") id: Long
    ): Response<ResponseBody>

    @Multipart
    @POST("/upload_image")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part?
    ): Response<ResponseBody>

    @Multipart
    @POST("/upload_video")
    suspend fun uploadVideo(
        @Part file: MultipartBody.Part?
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/create_surprise")
    suspend fun createSurprise(
        @Field("sender_id") senderId: Long,
        @Field("receiver_id") receiverId: Long,
        @Field("text") text: String?,
        @Field("file_type") fileType: TypeFile?,
        @Field("attachment_file_id") attachmentId: Long?,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/reject_surprise")
    suspend fun rejectSurprise(
        @Field("surprise_id") surpriseId: Long,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/update_reaction")
    suspend fun updateReaction(
        @Field("surprise_id") surpriseId: Long,
        @Field("reaction_file_id") reactionId: Long
    ): Response<ResponseBody>
}
