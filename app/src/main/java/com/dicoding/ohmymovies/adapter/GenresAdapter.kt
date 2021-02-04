package com.dicoding.ohmymovies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dicoding.ohmymovies.databinding.ItemGenreBinding
import com.ohmymovies.core.domain.model.GenreModel
import com.ohmymovies.core.ui.adapter.BaseAdapter
import com.ohmymovies.core.ui.adapter.BaseViewHolder
import com.ohmymovies.core.utils.DiffCallback

class GenresAdapter(clickCallback: (GenreModel) -> Unit) :
    BaseAdapter<BaseViewHolder<GenreModel>, GenreModel>(DiffCallback(), clickCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemGenreBinding) :
        BaseViewHolder<GenreModel>(binding.root) {

        override fun bind(data: GenreModel, onClickCallback: (GenreModel) -> Unit) {
            binding.genre = data.name
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGenreBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}