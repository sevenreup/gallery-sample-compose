package com.sevenreup.albumsample.network

import com.sevenreup.albumsample.data.model.MediaDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumNetworkService {
    @GET("/shared/{shareId}/media")
    suspend fun getMedia(
        @Path("shareId") shareId: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("m") mode: String
    ): Response<List<MediaDTO>>
}