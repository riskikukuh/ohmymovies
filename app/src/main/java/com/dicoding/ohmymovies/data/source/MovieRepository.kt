package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.TvShowModel
import kotlin.coroutines.CoroutineContext

interface MovieRepository {

    suspend fun getMovies(update : Boolean, context : Context) : Result<List<MovieModel>>

    suspend fun getTvShows(update : Boolean, context : Context) : Result<List<TvShowModel>>

}