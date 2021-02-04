package com.dicoding.ohmymovies.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.databinding.ItemTvshowBinding
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.utils.Constants
import com.ohmymovies.core.utils.DiffCallback

class TvshowsAdapter(
    private val diffCallback: DiffCallback = DiffCallback(),
    private val onClickCallback: (TvShowModel) -> Unit
) : RecyclerView.Adapter<BaseTvshowsViewHolder>() {

    private val tvshows = mutableListOf<TvShowModel>()

    companion object {
        const val TYPE_FAVORITE = 142
        const val TYPE_NOT_FAVORITE = 143
    }

    fun addTvshows(data: List<TvShowModel>) {
        diffCallback.setList(tvshows, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(tvshows) {
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (tvshows[position].isFavorite) {
            TYPE_FAVORITE
        } else {
            TYPE_NOT_FAVORITE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTvshowsViewHolder {
        return if (viewType == TYPE_FAVORITE) {
            ViewHolderFavorite.from(parent)
        } else {
            ViewHolderNotFavorite.from(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseTvshowsViewHolder, position: Int) {
        holder.bind(tvshows[position], onClickCallback)
    }

    override fun getItemCount(): Int = tvshows.size

    class ViewHolderFavorite private constructor(private val binding: ItemTvshowBinding) :
        BaseTvshowsViewHolder(binding.root) {
        override fun bind(data: TvShowModel, onClickCallback: (TvShowModel) -> Unit) {
            binding.tvshow = data
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.isTvshowFavorite.setImageResource(R.drawable.ic_favorite_24)
            binding.itemTvshow.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseTvshowsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTvshowBinding.inflate(layoutInflater, parent, false)
                return ViewHolderFavorite(binding)
            }
        }
    }

    class ViewHolderNotFavorite private constructor(private val binding: ItemTvshowBinding) :
        BaseTvshowsViewHolder(binding.root) {
        override fun bind(data: TvShowModel, onClickCallback: (TvShowModel) -> Unit) {
            binding.tvshow = data
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.isTvshowFavorite.setImageResource(R.drawable.ic_favorite_border_24)
            binding.itemTvshow.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseTvshowsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTvshowBinding.inflate(layoutInflater, parent, false)
                return ViewHolderNotFavorite(binding)
            }
        }
    }

}

abstract class BaseTvshowsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: TvShowModel, onClickCallback: (TvShowModel) -> Unit)
}