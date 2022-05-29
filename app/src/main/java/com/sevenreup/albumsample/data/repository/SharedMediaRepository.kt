package com.sevenreup.albumsample.data.repository

import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.network.AlbumNetworkService
import com.sevenreup.albumsample.utils.Response
import javax.inject.Inject

class SharedMediaRepository @Inject constructor(private val apiService: AlbumNetworkService) {

    suspend fun getMedia(
        shareId: String,
        width: Int,
        height: Int,
        mode: String
    ): Response<List<MediaDTO>?> {
        val response = apiService.getMedia(
            width = width,
            height = height,
            mode = mode,
            shareId = shareId
        )

        if (response.isSuccessful) {
            return Response.Success(data = response.body())
        }

        return Response.Failure(message = response.message())
    }

}