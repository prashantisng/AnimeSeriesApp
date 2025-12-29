package com.example.animeseriesapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animeseriesapp.data.local.AnimeDao
import com.example.animeseriesapp.data.local.AnimeItem

@Database(entities = [AnimeItem::class], version = 1, exportSchema = false)
abstract  class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
    companion object {
        // @Volatile ensures that the value of INSTANCE is always up-to-date and the same for all execution threads.
        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getDatabase(context: Context): AnimeDatabase {
            // Return the existing instance or create a new one if it doesn't exist.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "anime_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}