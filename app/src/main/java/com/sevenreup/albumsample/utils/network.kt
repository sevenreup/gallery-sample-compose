package com.sevenreup.albumsample.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.*
import java.util.concurrent.TimeUnit


const val CACHE_FILE = "offlineCache"

/**
 * This Interceptor adds all the required caching headers to the response to enable caching when online
 * This only does that if the server does not support caching
 * @return Interceptor
 */
fun provideOnlineInterceptor(): Interceptor {
    return Interceptor { chain: Interceptor.Chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }
}

/**
 * This interceptor retries the request with a header to get cache response header
 * @return Interceptor
 */
fun provideOfflineCacheInterceptor(context: Context): Interceptor {
    return Interceptor { chain: Interceptor.Chain ->
        var request = chain.request()
        if (!checkInternet(context = context)) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(1, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }
}

private fun checkInternet(context: Context): Boolean {
    var isConnected: Boolean = false // Initial Value
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}


