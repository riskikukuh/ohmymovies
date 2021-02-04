package com.ohmymovies.core.data.source.remote

import android.content.Context
import com.ohmymovies.core.R
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.Result.Error
import com.ohmymovies.core.data.Result.Success
import com.ohmymovies.core.data.source.remote.network.MoviesApi
import com.ohmymovies.core.data.source.remote.response.MovieResponse
import com.ohmymovies.core.data.source.remote.response.MoviesResponse
import com.ohmymovies.core.data.source.remote.response.TvShowResponse
import com.ohmymovies.core.data.source.remote.response.TvshowsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieRemoteDataSource(
    context: Context,
    private val service: MoviesApi,
    private val apiKey: String = ""
) {

    private val unknownError = context.getString(R.string.unknown_error)

    suspend fun getMovies(): Result<MoviesResponse> {
        return try {
            val response = service.getMovies(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(unknownError)
            }
        } catch (e: Exception) {
            Error(e.message ?: unknownError)
        }
    }

    suspend fun getTvshows(): Result<TvshowsResponse> {
        return try {
            val response = service.getTvshows(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(unknownError)
            }
        } catch (e: Exception) {
            Error(e.message ?: unknownError)
        }
    }

    suspend fun getMovie(id: Int): Result<MovieResponse> {
        return try {
            val response = service.getDetailMovie(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(unknownError)
            }
        } catch (e: Exception) {
            Error(e.message ?: unknownError)
        }
    }

    suspend fun getTvshow(id: Int): Result<TvShowResponse> {
        return try {
            val response = service.getDetailTvshow(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(unknownError)
            }
        } catch (e: Exception) {
            Error(e.message ?: unknownError)
        }

    }

}