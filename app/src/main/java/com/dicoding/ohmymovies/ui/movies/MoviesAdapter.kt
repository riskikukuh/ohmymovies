package com.dicoding.ohmymovies.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.databinding.ItemMovieBinding
import com.dicoding.ohmymovies.util.DiffCallback

class MoviesAdapter(private val diffCallback: DiffCallback = DiffCallback(), private val onClickCallback : (MovieModel) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private val movies = mutableListOf<MovieModel>()

    fun addMovies(data : List<MovieModel>){
        diffCallback.setList(movies, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(movies){
            clear()
            addAll(data)
        }
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        holder.bind(movies[position], onClickCallback)
    }

    class ViewHolder private constructor(private val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: MovieModel, onClickCallback: (MovieModel) -> Unit){
            binding.movie = data
            binding.imagePoster.setImageResource(data.posterImageResource)
            binding.itemMovie.setOnClickListener {
                onClickCallback.invoke(data)
            }
        }

        companion object{
            fun from(parent : ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}