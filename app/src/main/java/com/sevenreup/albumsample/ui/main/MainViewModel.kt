package com.sevenreup.albumsample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.RequestMessageOptions
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.data.repository.AppPreferenceRepository
import com.sevenreup.albumsample.data.repository.SharedMediaRepository
import com.sevenreup.albumsample.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SharedMediaRepository,
    private val appPrefRepository: AppPreferenceRepository
) : ViewModel() {
    val prefs = MutableLiveData<AppPreferences>()
    val mediaResponse = MutableLiveData<Response<List<MediaDTO>?>>(Response.Loading())
    val selected = MutableLiveData<MediaDTO>(null)

    val savingEdits = MutableLiveData<Response<Boolean>>(Response.Idle())

    init {
        viewModelScope.launch {
            appPrefRepository.appPreferenceFlow.collect {
                prefs.value = it
                getMedia(it)
            }
        }
    }

    private suspend fun getMedia(appPreferences: AppPreferences) {
        mediaResponse.value = Response.Loading()

        mediaResponse.value = repository.getMedia(
            appPreferences.shareId,
            appPreferences.options.width,
            appPreferences.options.height,
            appPreferences.options.mode
        )
    }

    fun editPreferences(shareID: String, options: RequestMessageOptions) {
        savingEdits.value = Response.Loading()

        viewModelScope.launch {
            appPrefRepository.editPreferences(shareId = shareID, options = options)
            savingEdits.value = Response.Success(true)
        }
    }

    fun refreshRefresh() {
        viewModelScope.launch {
            val prefs = prefs.value

            if (prefs != null) {
                getMedia(appPreferences = prefs)
            }
        }
    }
}