package com.dicoding.ohmymovies.ui.favoriteMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.source.MovieRepository

class FavoriteMoviesViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    fun fetchFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>> =
        repository.getFavoriteMovies()
}