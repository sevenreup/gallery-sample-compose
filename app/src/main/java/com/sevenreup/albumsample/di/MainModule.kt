package com.sevenreup.albumsample.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.network.AlbumNetworkService
import com.sevenreup.albumsample.utils.appPreferencesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun providesRetrofit(): AlbumNetworkService {
        return Retrofit
            .Builder()
            .baseUrl("https://api1.kiliaro.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumNetworkService::class.java)
    }

    @Provides
    fun providesAppPreference(@ApplicationContext context: Context): DataStore<AppPreferences> =
        context.appPreferencesStore
}