package com.example.animeseriesapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeseriesapp.R
import com.example.animeseriesapp.data.local.AnimeItem
import com.example.animeseriesapp.databinding.ItemAnimeBinding

// In adapter/AnimeAdapter.kt
class AnimeAdapter(private val onItemClicked:(AnimeItem)->Unit) : ListAdapter<AnimeItem, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding,onItemClicked)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding,
                                private val onItemClicked: (AnimeItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("StringFormatMatches")
            fun bind(animeItem: AnimeItem) {
            binding.apply {
                tvTitle.text = animeItem.title

                // Format rating and episodes text
                tvRating.text = animeItem.score?.toString() ?: "N/A"
                // The warning will now disappear from this line
                tvEpisodes.text = itemView.context.getString(R.string.episode_count, animeItem.episodes ?: 0)
               // tvEpisodes.text=animeItem.episodes+"Episodes".toString()
                // Use Glide (or another image loading library) to load the poster
                Glide.with(itemView.context)
                    .load(animeItem.imageUrl)
                    .placeholder(R.drawable.ic_placeholder) // Optional: a placeholder image
                    .error(R.drawable.ic_placeholder)       // Optional: an error image
                    .into(ivPoster)
                itemView.setOnClickListener {
                    onItemClicked(animeItem)
                }
            }
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeItem>() {
        override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean {
            return oldItem == newItem
        }
    }
}