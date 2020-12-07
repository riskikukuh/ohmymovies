package com.dicoding.ohmymovies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowWithGenreLanguage

interface MovieRepository {

    suspend fun getMovies(): Result<List<MovieModel>>

    suspend fun getTvShows(): Result<List<TvShowModel>>

    suspend fun getMovie(id: Int): Result<MovieModel>

    suspend fun getTvshow(id: Int): Result<TvShowModel>

    fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>>

    fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>>

    suspend fun getFavoriteMovie(id: Int): Result<MovieWithGenreLanguage>

    suspend fun getFavoriteTvshow(id: Int): Result<TvshowWithGenreLanguage>

    suspend fun addMovieToFavorite(movie: MovieModel): Result<Boolean>

    suspend fun addTvshowToFavorite(tvshow: TvShowModel): Result<Boolean>

    suspend fun removeMovieFromFavorite(id: Int): Result<Boolean>

    suspend fun removeTvshowFromFavorite(id: Int): Result<Boolean>

}