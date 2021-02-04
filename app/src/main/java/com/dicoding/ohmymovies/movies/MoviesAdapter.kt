package com.dicoding.ohmymovies.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.databinding.ItemMovieBinding
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.ui.adapter.BaseAdapter
import com.ohmymovies.core.ui.adapter.BaseViewHolder
import com.ohmymovies.core.utils.Constants
import com.ohmymovies.core.utils.DiffCallback

class MoviesAdapter(clickCallback: (MovieModel) -> Unit) :
    BaseAdapter<BaseViewHolder<MovieModel>, MovieModel>(
        DiffCallback(), clickCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MovieModel> {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemMovieBinding) :
        BaseViewHolder<MovieModel>(binding.root) {
        override fun bind(data: MovieModel, onClickCallback: (MovieModel) -> Unit) {
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.movie = data
            binding.itemMovie.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseViewHolder<MovieModel> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}
