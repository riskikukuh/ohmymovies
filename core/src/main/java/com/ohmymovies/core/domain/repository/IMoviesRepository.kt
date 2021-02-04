package com.ohmymovies.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.source.local.entity.MovieEntity
import com.ohmymovies.core.data.source.local.entity.TvshowEntity
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.domain.model.TvShowModel
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    suspend fun getMovies(): Flow<Result<List<MovieModel>>>
    suspend fun getTvshows(): Flow<Result<List<TvShowModel>>>

    suspend fun getMovie(id: Int = 0): Flow<Result<MovieModel>>
    suspend fun getTvshow(id: Int = 0): Flow<Result<TvShowModel>>

    fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>>
    fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>>

    suspend fun getFavoriteMovie(id: Int): Result<MovieModel>
    suspend fun getFavoriteTvshow(id: Int): Result<TvShowModel>

    suspend fun addMovieToFavorite(movieModel: MovieModel): Result<Boolean>
    suspend fun addTvshowToFavorite(tvShowModel: TvShowModel): Result<Boolean>

    suspend fun removeFavoriteMovie(id: Int = 0): Result<Boolean>
    suspend fun removeFavoriteTvshow(id: Int = 0): Result<Boolean>

}