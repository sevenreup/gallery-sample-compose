package com.sevenreup.albumsample.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.RequestMessageOptions
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.data.repository.AppPreferenceRepository
import com.sevenreup.albumsample.data.repository.SharedMediaRepository
import com.sevenreup.albumsample.utils.DefaultValues
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
    val mediaList = MutableLiveData<List<MediaDTO>>(listOf())
    val selected = MutableLiveData<MediaDTO>(null)

    val savingEdits = MutableLiveData<Response<Boolean>>(Response.Idle())

    init {
        viewModelScope.launch {
            appPrefRepository.appPreferenceFlow.collect {
                prefs.value = it
            }
        }
        viewModelScope.launch {
            mediaList.value = repository.getMedia()
        }
    }

    fun editPreferences(shareID: String, options: RequestMessageOptions) {
        savingEdits.value = Response.Loading()

        viewModelScope.launch {
            appPrefRepository.editPreferences(shareId = shareID, options = options)
            savingEdits.value = Response.Success(true)
        }
    }
}