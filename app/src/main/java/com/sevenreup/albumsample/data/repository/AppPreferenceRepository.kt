package com.sevenreup.albumsample.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.RequestMessageOptions
import com.sevenreup.albumsample.utils.DefaultValues
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class AppPreferenceRepository @Inject constructor(private val store: DataStore<AppPreferences>) {

    val appPreferenceFlow = store.data.catch { exception ->
        if (exception is IOException) {
            Log.e(TAG, "Error reading sort order preferences.", exception)
            emit(DefaultValues)
        } else {
            throw exception
        }
    }

    suspend fun editPreferences(shareId: String, options: RequestMessageOptions) {
        Log.e(TAG, "Saving: $shareId, $options")
        store.updateData { currentAppPref ->
            currentAppPref.toBuilder().setShareId(shareId).setOptions(options).build()
        }
    }

    companion object {
        const val TAG = "AppPreferenceRepository"
    }
}