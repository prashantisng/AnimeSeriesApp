package com.example.animeseriesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    // Fetches all anime items from the database. Returns a Flow so the UI can update automatically.
    @Query("SELECT * FROM anime_items")
    fun getAllAnime(): Flow<List<AnimeItem>>

    // Inserts a list of anime items. If an item already exists, it will be replaced.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeItems: List<AnimeItem>)

    // Deletes all items from the table. We'll use this to refresh the data.
    @Query("DELETE FROM anime_items")
    suspend fun clearAll()

}