package com.example.animeseriesapp.viewmodel

import androidx.activity.result.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.animeseriesapp.data.local.AnimeItem
import com.example.animeseriesapp.data.repository.AnimeRepository
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: AnimeRepository) : ViewModel(){
    val allAnime: LiveData<List<AnimeItem>> =
        repository.allAnime.asLiveData()

    // A state to represent loading status
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        // Refresh data when the ViewModel is created
        refreshData()
    }
    private fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshTopAnime()
            } finally {
                _isLoading.value = false
            }
        }
    }


}
//Factory to create an instance of AnimeViewModel with a repository parameter.
class AnimeViewModelFactory(private val repository: AnimeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
