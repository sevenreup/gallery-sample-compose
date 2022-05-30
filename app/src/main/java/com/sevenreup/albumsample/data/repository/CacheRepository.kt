package com.sevenreup.albumsample.data.repository

import android.content.Context
import com.bumptech.glide.Glide
import okhttp3.Cache
import javax.inject.Inject

class CacheRepository @Inject constructor(private val cache: Cache) {
    fun clearCache(context: Context) {
        Glide.get(context).clearDiskCache()
        cache.delete()
    }
}