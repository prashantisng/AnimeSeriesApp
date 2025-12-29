package com.example.animeseriesapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animeseriesapp.data.local.db.AnimeDatabase
import com.example.animeseriesapp.data.repository.AnimeRepository
import com.example.animeseriesapp.databinding.ActivityMainBinding
import com.example.animeseriesapp.ui.adapters.AnimeAdapter
import com.example.animeseriesapp.ui.adapter.AnimeDetailsActivity
import com.example.animeseriesapp.viewmodel.AnimeViewModel
import com.example.animeseriesapp.viewmodel.AnimeViewModelFactory
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var animeAdapter: AnimeAdapter

    private val animeViewModel: AnimeViewModel by viewModels {
        // Create the factory to provide the ViewModel with its dependencies
        AnimeViewModelFactory(
            AnimeRepository(AnimeDatabase.getDatabase(this).animeDao())
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpRecyclerView()
        observeAnimeData()

    }
    private fun setUpRecyclerView(){
        animeAdapter = AnimeAdapter{animeItem ->
            val intent= Intent(this, AnimeDetailsActivity::class.java).apply{
                putExtra("ANIME_ID",animeItem.mal_id)
            }
            startActivity(intent)
        }
        binding.rvAnime.apply {
            adapter = animeAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }
    private fun observeAnimeData() {
        // Observe the list of anime from the ViewModel
        animeViewModel.allAnime.observe(this) { animeList ->
            // When data changes, submit it to the adapter.
            // Also, show progress bar only if the list is empty during initial load.
            animeAdapter.submitList(animeList)
            if (animeList.isEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        // Observe the loading state for the swipe-to-refresh
        animeViewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
            // Hide the main progress bar if swipe-to-refresh is finished
            if (!isLoading && binding.progressBar.visibility == View.VISIBLE) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}