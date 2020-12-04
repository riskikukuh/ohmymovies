package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity

interface MovieRepository {

    suspend fun getMovies() : Result<List<MovieModel>>

    suspend fun getTvShows() : Result<List<TvShowModel>>

    suspend fun getMovie(id : Int) : Result<MovieModel>

    suspend fun getTvshow(id : Int) : Result<TvShowModel>

    suspend fun getFavoriteMovies() : Result<List<MovieEntity>>

    suspend fun getFavoriteTvshows() : Result<List<TvshowEntity>>

    suspend fun getFavoriteMovie(context: Context, id : Int) : Result<MovieEntity>

    suspend fun getFavoriteTvshow(context : Context, id :Int) : Result<TvshowEntity>

}