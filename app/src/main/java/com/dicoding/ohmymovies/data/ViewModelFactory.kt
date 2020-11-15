package com.dicoding.ohmymovies.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.ui.detailMovie.DetailMovieViewModel
import com.dicoding.ohmymovies.ui.detailTvshow.DetailTvshowViewModel
import com.dicoding.ohmymovies.ui.movies.MoviesViewModel
import com.dicoding.ohmymovies.ui.tvshows.TvshowsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val application: Application,
    private val movieRepository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MoviesViewModel::class.java) -> {
                    MoviesViewModel(application, movieRepository)
                }
                isAssignableFrom(TvshowsViewModel::class.java) -> {
                    TvshowsViewModel(application, movieRepository)
                }
                isAssignableFrom(DetailMovieViewModel::class.java) -> {
                    DetailMovieViewModel(application)
                }
                isAssignableFrom(DetailTvshowViewModel::class.java) -> {
                    DetailTvshowViewModel(application)
                }
                else -> {
                    throw IllegalArgumentException("Unknown Viewmodel class")
                }
            }
        } as T


}