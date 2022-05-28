package com.sevenreup.albumsample.data.model

import com.google.gson.annotations.SerializedName

data class MediaDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("filename")
    var filename: String,
    @SerializedName("size")
    var size: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("taken_at")
    var takenAt: String? = null,
    @SerializedName("guessed_taken_at")
    var guessedTakenAt: String? = null,
    @SerializedName("md5sum")
    var md5sum: String,
    @SerializedName("content_type")
    var contentType: String,
    @SerializedName("video")
    var video: String? = null,
    @SerializedName("thumbnail_url")
    var thumbnailUrl: String,
    @SerializedName("download_url")
    var downloadUrl: String,
    @SerializedName("resx")
    var resx: Int,
    @SerializedName("resy")
    var resy: Int
)