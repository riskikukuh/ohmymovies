package com.dicoding.ohmymovies.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.databinding.ItemTvshowBinding
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.ui.adapter.BaseAdapter
import com.ohmymovies.core.ui.adapter.BaseViewHolder
import com.ohmymovies.core.utils.Constants
import com.ohmymovies.core.utils.DiffCallback

class TvshowsAdapter(clickCallback: (TvShowModel) -> Unit) :
    BaseAdapter<BaseViewHolder<TvShowModel>, TvShowModel>(DiffCallback(), clickCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TvShowModel> {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemTvshowBinding) :
        BaseViewHolder<TvShowModel>(binding.root) {

        override fun bind(data: TvShowModel, onClickCallback: (TvShowModel) -> Unit) {
            binding.tvshow = data
            Glide.with(binding.root.context).load(Constants.BASE_URL_POSTER + data.posterPath)
                .into(binding.imagePoster)
            binding.itemTvshow.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object {
            fun from(parent: ViewGroup): BaseViewHolder<TvShowModel> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTvshowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

