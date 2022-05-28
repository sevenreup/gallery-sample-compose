package com.sevenreup.albumsample.di

import com.sevenreup.albumsample.network.AlbumNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

@Provides
fun providesRetrofit(): AlbumNetworkService {
    return  Retrofit
        .Builder()
        .baseUrl("https://api1.kiliaro.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AlbumNetworkService::class.java)
}

}