package com.dicoding.ohmymovies.ui.favoriteTvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.databinding.ItemTvshowBinding
import com.dicoding.ohmymovies.util.Constants

class FavoriteTvshowsAdapter internal constructor(
    private val onClickCallback: (TvshowEntity) -> Unit
) : PagedListAdapter<TvshowEntity, FavoriteTvshowsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvshowEntity>() {
            override fun areItemsTheSame(oldItem: TvshowEntity, newItem: TvshowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvshowEntity, newItem: TvshowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder private constructor(private val binding: ItemTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvshowEntity, onClickCallback: (TvshowEntity) -> Unit) {
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.tvshow = TvShowModel.fromTvshowEntity(data)
            binding.isTvshowFavorite.setImageResource(R.drawable.ic_favorite_24)
            binding.itemTvshow.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTvshowBinding.inflate(layoutInflater, parent, false)
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