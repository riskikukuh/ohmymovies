package com.dicoding.ohmymovies.ui.favoriteMovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.MovieEntity
import com.ohmymovies.core.domain.usecase.MovieUseCase

class FavoriteMoviesViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    fun fetchFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>> =
        movieUseCase.getFavoriteMovies()
}