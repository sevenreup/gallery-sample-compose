package com.sevenreup.albumsample.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.network.AlbumNetworkService
import com.sevenreup.albumsample.utils.CACHE_FILE
import com.sevenreup.albumsample.utils.appPreferencesStore
import com.sevenreup.albumsample.utils.provideOfflineCacheInterceptor
import com.sevenreup.albumsample.utils.provideOnlineInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideCache(@ApplicationContext context: Context) =
        Cache(File(context.cacheDir, CACHE_FILE), 10 * 1024 * 1024)

    @Provides
    fun providesRetrofit(@ApplicationContext context: Context, cache: Cache): AlbumNetworkService {
        return Retrofit
            .Builder()
            .baseUrl("https://api1.kiliaro.com/")
            .client(
                OkHttpClient()
                    .newBuilder()
                    .cache(cache)
                    .addNetworkInterceptor(provideOnlineInterceptor())
                    .addNetworkInterceptor(provideOfflineCacheInterceptor(context))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumNetworkService::class.java)
    }

    @Provides
    fun providesAppPreference(@ApplicationContext context: Context): DataStore<AppPreferences> =
        context.appPreferencesStore
}