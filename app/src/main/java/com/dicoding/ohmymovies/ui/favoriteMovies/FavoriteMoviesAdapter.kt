package com.dicoding.ohmymovies.ui.favoriteMovies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.databinding.ItemMovieBinding
import com.ohmymovies.core.data.source.local.entity.MovieEntity
import com.ohmymovies.core.utils.Constants
import com.ohmymovies.core.utils.DataMapper

class FavoriteMoviesAdapter internal constructor(
    private val onClickCallback: (MovieEntity) -> Unit
) : PagedListAdapter<MovieEntity, FavoriteMoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder private constructor(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity, onClickCallback: (MovieEntity) -> Unit) {
            Log.d("AdapterKuy", "movie: $data")
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.movie = DataMapper.mapMovieEntityToMovieModel(data)
            binding.isMovieFavorite.setImageResource(R.drawable.ic_favorite_24)
            binding.itemMovie.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it, onClickCallback)
        }
    }

}