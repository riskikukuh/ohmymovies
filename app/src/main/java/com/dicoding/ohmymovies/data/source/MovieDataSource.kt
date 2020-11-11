package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.TvShowModel

interface MovieDataSource {
    suspend fun getMovies(context : Context) : Result<List<MovieModel>>

    suspend fun getTvshows(context : Context) : Result<List<TvShowModel>>
}