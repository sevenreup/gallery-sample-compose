package com.sevenreup.albumsample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenreup.albumsample.data.model.MediaDTO
import com.sevenreup.albumsample.data.repository.SharedMediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SharedMediaRepository) : ViewModel() {

    val mediaList = MutableLiveData<List<MediaDTO>>(listOf())
    val selected = MutableLiveData<MediaDTO>(null)

    init {
        viewModelScope.launch {
            mediaList.value  = repository.getMedia()
        }
    }
}