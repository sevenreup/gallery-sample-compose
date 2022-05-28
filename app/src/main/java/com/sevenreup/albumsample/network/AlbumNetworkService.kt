package com.sevenreup.albumsample.network

import com.sevenreup.albumsample.data.model.MediaDTO
import retrofit2.Response
import retrofit2.http.GET

interface AlbumNetworkService {
    @GET("/shared/djlCbGusTJamg_ca4axEVw/media?w=80&h=80&m=bb")
    suspend fun getMedia(): Response<List<MediaDTO>>
}