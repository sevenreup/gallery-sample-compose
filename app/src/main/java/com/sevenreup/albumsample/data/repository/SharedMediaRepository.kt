package com.sevenreup.albumsample.data.repository

import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.network.AlbumNetworkService
import javax.inject.Inject

class SharedMediaRepository @Inject constructor(val apiService: AlbumNetworkService) {

    suspend fun getMedia(): List<MediaDTO>? {
        val response = apiService.getMedia()

        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }
    
}