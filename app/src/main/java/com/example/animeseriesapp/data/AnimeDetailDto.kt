package com.example.animeseriesapp.data

import com.example.animeseriesapp.Genre
import com.example.animeseriesapp.Images
import com.example.animeseriesapp.Trailer

data class AnimeDetailResponse(
    val data: AnimeDetailDto
)

data class AnimeDetailDto(
    val mal_id: Int,
    val url: String?,
    val images: Images,
    val trailer: Trailer?,
    val title: String,
    val title_english: String?,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val year: Int?,
    val genres: List<Genre>,
)
