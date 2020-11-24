package com.dicoding.ohmymovies.data.source.remote

import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.api.MoviesApi
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.MoviesResponse
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.TvshowsResponse
import com.dicoding.ohmymovies.data.source.MovieDataSource

class MovieRemoteDataSource(private val service: MoviesApi, private val apiKey: String = "") :
    MovieDataSource {
    override suspend fun getMovies(): Result<MoviesResponse> {
        return try {
            val response = service.getMovies(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception("Something wrong occurred"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTvshows(): Result<TvshowsResponse> {
        return try {
            val response = service.getTvshows(apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception("Something wrong occurred"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getMovie(id: Int): Result<MovieModel> {
        return try {
            val response = service.getDetailMovie(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception("Something wrong occurred"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTvshow(id: Int): Result<TvShowModel> {
        return try {
            val response = service.getDetailTvshow(id, apiKey)
            if (response.isSuccessful && response.body() != null) {
                Success(response.body()!!)
            } else {
                Error(Exception("Something wrong occurred"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }


}