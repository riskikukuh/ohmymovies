package com.dicoding.ohmymovies.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.databinding.ItemMovieBinding
import com.dicoding.ohmymovies.util.Constants
import com.dicoding.ohmymovies.util.DiffCallback

class MoviesAdapter(
    private val diffCallback: DiffCallback = DiffCallback(),
    private val onClickCallback: (MovieModel) -> Unit
) : RecyclerView.Adapter<BaseMovieViewHolder>() {

    private val movies = mutableListOf<MovieModel>()

    companion object {
        const val TYPE_FAVORITE = 122
        const val TYPE_NOT_FAVORITE = 123
    }

    fun addMovies(data: List<MovieModel>) {
        diffCallback.setList(movies, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(movies) {
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieViewHolder {
        return if (viewType == TYPE_FAVORITE) {
            ViewHolderFavorite.from(parent)
        } else {
            ViewHolderNotFavorite.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position].isFavorite) {
            TYPE_FAVORITE
        } else {
            TYPE_NOT_FAVORITE
        }
    }

    override fun onBindViewHolder(holder: BaseMovieViewHolder, position: Int) {
        holder.bind(movies[position], onClickCallback)
    }

    class ViewHolderFavorite private constructor(private val binding: ItemMovieBinding) :
        BaseMovieViewHolder(binding.root) {
        override fun bind(data: MovieModel, onClickCallback: (MovieModel) -> Unit) {
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.movie = data
            binding.isMovieFavorite.setImageResource(R.drawable.ic_favorite_24)
            binding.itemMovie.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseMovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolderFavorite(binding)
            }
        }
    }

    class ViewHolderNotFavorite private constructor(private val binding: ItemMovieBinding) :
        BaseMovieViewHolder(binding.root) {
        override fun bind(data: MovieModel, onClickCallback: (MovieModel) -> Unit) {
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.movie = data
            binding.isMovieFavorite.setImageResource(R.drawable.ic_favorite_border_24)
            binding.itemMovie.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseMovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolderNotFavorite(binding)
            }
        }
    }

}

abstract class BaseMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: MovieModel, onClickCallback: (MovieModel) -> Unit)
}