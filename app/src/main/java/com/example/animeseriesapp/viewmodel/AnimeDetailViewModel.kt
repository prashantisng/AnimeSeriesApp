package com.example.animeseriesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeseriesapp.data.AnimeDetailDto
import com.example.animeseriesapp.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class AnimeDetailViewModel : ViewModel() {
    private val _animeDetails = MutableLiveData<AnimeDetailDto?>()
    val animeDetails: LiveData<AnimeDetailDto?> = _animeDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchAnimeDetails(animeId:Int){
        viewModelScope.launch {
            _isLoading.value=true
            try {
                val response= RetrofitInstance.api.getAnimeDetails(animeId)
                if(response.isSuccessful){
                    _animeDetails.value=response.body()?.data
                    //_error.value = null
                }else{
                    _error.value="Failed to fetch anime details"
                }
            }catch (e: Exception){
                _error.value=e.message
            }finally {
                _isLoading.value=false
            }
        }
    }
}