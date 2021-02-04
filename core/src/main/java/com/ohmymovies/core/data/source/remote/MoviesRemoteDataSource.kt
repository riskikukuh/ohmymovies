package com.ohmymovies.core.data.source.remote

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
class MovieRemoteDataSource(private val service: MoviesApi, private val apiKey: String = "") {

    suspend fun getMovies(): Result<MoviesResponse> {
        return try {
            val response = service.getMovies(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error("Something wrong occurred")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Something wrong occurred")
        }
    }

    suspend fun getTvshows(): Result<TvshowsResponse> {
        return try {
            val response = service.getTvshows(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error("Something wrong occurred")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Something wrong occurred")
        }
    }

    suspend fun getMovie(id: Int): Result<MovieResponse> {
        return try {
            val response = service.getDetailMovie(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error("Something wrong occurred")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Something wrong occurred")
        }
    }

    suspend fun getTvshow(id: Int): Result<TvShowResponse> {
        return try {
            val response = service.getDetailTvshow(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error("Something wrong occurred")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Something wrong occurred")
        }

    }

}