package com.dicoding.ohmymovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ohmymovies.databinding.ItemGenreBinding
import com.ohmymovies.core.domain.model.GenreModel
import com.ohmymovies.core.utils.DiffCallback

class GenresAdapter(
    private val genres: MutableList<GenreModel> = mutableListOf(),
    private val diffCallback: DiffCallback = DiffCallback()
) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    fun addGenres(data: List<GenreModel>) {
        diffCallback.setList(genres, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(genres) {
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int = genres.size

    class ViewHolder private constructor(private val binding : ItemGenreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: GenreModel) {
            binding.genre = data.name
        }

        companion object{
            fun from(parent : ViewGroup) : ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemGenreBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}