package com.dicoding.ohmymovies.data.source

import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.MoviesResponse
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.TvshowsResponse

interface MovieDataSource {
    suspend fun getMovies() : Result<MoviesResponse>

    suspend fun getTvshows() : Result<TvshowsResponse>

    suspend fun getMovie(id : Int) : Result<MovieModel>

    suspend fun getTvshow(id : Int) : Result<TvShowModel>
}