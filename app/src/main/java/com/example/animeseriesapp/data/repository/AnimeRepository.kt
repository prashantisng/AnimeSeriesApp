package com.example.animeseriesapp.data.repository

import android.util.Log

import com.example.animeseriesapp.data.local.AnimeDao
import com.example.animeseriesapp.data.local.AnimeItem
import com.example.animeseriesapp.data.local.toAnimeItem
import com.example.animeseriesapp.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class AnimeRepository(private val animeDao: AnimeDao) {
    // The Flow from Room will automatically update the UI when the database changes.
    val allAnime: Flow<List<AnimeItem>> = animeDao.getAllAnime()
    /**
     * Refreshes the anime data from the network and stores it in the database.
     * This is the "syncing" part.
     */
    suspend fun refreshTopAnime() {
        try {
            val response = RetrofitInstance.api.getTopAnime()
            if (response.isSuccessful && response.body() != null) {
                // Map the network DTO to our database Entity
                val networkAnimeItems = response.body()!!.data.map { it.toAnimeItem() }
                // Insert the fresh data into the database
                animeDao.insertAll(networkAnimeItems)
                Log.d("AnimeRepository", "Data refreshed successfully from network.")
            } else {
                Log.e("AnimeRepository", "API Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("AnimeRepository", "Network Exception: ${e.message}")
            // In case of a network error, the app will continue to show data from the Flow (if any exists).
        }
    }

}