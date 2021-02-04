package com.ohmymovies.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.MovieEntity
import com.ohmymovies.core.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    suspend fun getMovies(): Flow<Result<List<MovieModel>>>
    suspend fun getMovie(id: Int): Flow<Result<MovieModel>>
    fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>>
    suspend fun addFavoriteMovie(data: MovieModel): Result<Boolean>
    suspend fun removeFavoriteMovie(id: Int): Result<Boolean>
    suspend fun getFavoriteMovie(id: Int): Result<MovieModel>
}