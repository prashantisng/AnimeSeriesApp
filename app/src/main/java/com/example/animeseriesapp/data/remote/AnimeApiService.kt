package com.example.animeseriesapp.data.remote

import com.example.animeseriesapp.AnimeDto
import com.example.animeseriesapp.data.AnimeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApiService {

    @GET("top/anime")
    suspend fun  getTopAnime() : Response<AnimeDto>

    @GET("anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId:Int
    ): Response<AnimeDetailResponse>
}