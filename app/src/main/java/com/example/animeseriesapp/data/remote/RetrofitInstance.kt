package com.example.animeseriesapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // The base URL for the Jikan API.
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val api: AnimeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // GsonConverterFactory tells Retrofit how to parse the JSON response into our data classes.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApiService::class.java)
    }

}