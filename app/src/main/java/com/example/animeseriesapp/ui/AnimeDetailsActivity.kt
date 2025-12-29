package com.example.animeseriesapp.ui.adapter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.animeseriesapp.R
import com.example.animeseriesapp.data.AnimeDetailDto
import com.example.animeseriesapp.databinding.ActivityAnimeDetailsBinding
import com.example.animeseriesapp.viewmodel.AnimeDetailViewModel
import com.google.android.material.chip.Chip

class AnimeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeDetailsBinding
    private val viewModel: AnimeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityAnimeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Set up the toolbar for a back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val animeId = intent.getIntExtra("ANIME_ID", -1)
        if (animeId != -1) {
            viewModel.fetchAnimeDetails(animeId)
        }
        setupObservers()
    }
    private fun setupObservers(){
        viewModel.isLoading.observe(this){isLoading ->
            binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.animeDetails.observe(this) {anime->
            anime?.let {
                populateUi(it)
            }
        }
    }

    private fun populateUi(anime: AnimeDetailDto) {
        // Set title on collapsing toolbar
        binding.collapsingToolbar.title = anime.title

        // Set poster image
        Glide.with(this).load(anime.images.jpg.large_image_url).into(binding.ivDetailPoster)

        // Set title and synopsis in the body
        binding.tvDetailTitle.text = anime.title_english ?: anime.title
        binding.tvSynopsis.text = anime.synopsis ?: "No synopsis available."

        // Handle trailer click
        val trailerUrl = anime.trailer?.url
        if (!trailerUrl.isNullOrEmpty()) {
            binding.ivPlayButton.visibility = View.VISIBLE
            binding.ivDetailPoster.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl as String?)))
            }
        } else {
            binding.ivPlayButton.visibility = View.GONE
        }
        // Populate genres
        binding.chipGroupGenres.removeAllViews()
        anime.genres.forEach { genre ->
            val chip = Chip(this).apply {
                text = genre.name
            }
            binding.chipGroupGenres.addView(chip)
        }
    }

    // Handle the back button in the toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}

private fun Any?.isNullOrEmpty(): Boolean {
    return this==null
}
