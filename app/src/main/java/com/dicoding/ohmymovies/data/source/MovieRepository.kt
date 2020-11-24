package com.dicoding.ohmymovies.data.source

import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel

interface MovieRepository {

    suspend fun getMovies() : Result<List<MovieModel>>

    suspend fun getTvShows() : Result<List<TvShowModel>>

    suspend fun getMovie(id : Int) : Result<MovieModel>

    suspend fun getTvshow(id : Int) : Result<TvShowModel>

}