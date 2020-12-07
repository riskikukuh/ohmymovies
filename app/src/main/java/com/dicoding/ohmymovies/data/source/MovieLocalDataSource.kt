package com.dicoding.ohmymovies.data.source

import androidx.paging.DataSource
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowWithGenreLanguage

interface MovieLocalDataSource {
    fun getFavoriteMovies(): Result<DataSource.Factory<Int, MovieEntity>>

    fun getFavoriteTvshows(): Result<DataSource.Factory<Int, TvshowEntity>>

    suspend fun getFavoriteMovie(id: Int): Result<MovieWithGenreLanguage>

    suspend fun getFavoriteTvshow(id: Int): Result<TvshowWithGenreLanguage>

    suspend fun addMovieToFavorite(vararg movieEntity: MovieEntity): Result<Boolean>

    suspend fun addTvshowToFavorite(vararg tvshow: TvshowEntity): Result<Boolean>

    suspend fun deleteMovieFromFavorite(movie: MovieWithGenreLanguage): Result<Boolean>

    suspend fun deleteTvshowFromFavorite(tvshow: TvshowWithGenreLanguage): Result<Boolean>
}