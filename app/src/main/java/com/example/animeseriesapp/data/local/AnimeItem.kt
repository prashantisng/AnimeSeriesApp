package com.example.animeseriesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animeseriesapp.Data

@Entity(tableName = "anime_items")
data class AnimeItem(
    @PrimaryKey
    val mal_id: Int,

    // The title of the anime.
    val title: String,

    // The number of episodes.
    val episodes: Int?, // Making it nullable in case some anime don't have a fixed episode count

    // The rating score from the API.
    val score: Double?, // Making it nullable for safety

    // The URL for the poster image.
    val imageUrl: String

)

// This function takes a 'Data' object from the network response...
fun Data.toAnimeItem(): AnimeItem {
    // ...and returns a simplified 'AnimeItem' object.
    return AnimeItem(
        mal_id = this.mal_id,
        title = this.title_english ?: this.title, // Use English title, fallback to main title
        episodes = this.episodes,
        score = this.score,
        // We get the URL for the large JPG image from the nested 'images' object.
        imageUrl = this.images.jpg.large_image_url
    )
}