package com.dicoding.ohmymovies.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.databinding.ItemTvshowBinding
import com.dicoding.ohmymovies.util.DiffCallback

class TvshowsAdapter(private val diffCallback: DiffCallback = DiffCallback(), private val onClickCallback : (TvShowModel) -> Unit) : RecyclerView.Adapter<TvshowsAdapter.ViewHolder>(){

    private val tvshows = mutableListOf<TvShowModel>()

    fun addTvshows(data : List<TvShowModel>){
        diffCallback.setList(tvshows, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(tvshows){
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvshows[position], onClickCallback)
    }

    override fun getItemCount(): Int = tvshows.size

    class ViewHolder private constructor(private val binding : ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : TvShowModel, onClickCallback: (TvShowModel) -> Unit){
            binding.tvshow = data
            binding.imagePoster.setImageResource(data.posterImageResource)
            binding.itemTvshow.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object{
            fun from(parent : ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTvshowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}