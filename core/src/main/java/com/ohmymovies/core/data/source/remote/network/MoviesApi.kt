package com.ohmymovies.core.data.source.remote.network

import com.ohmymovies.core.data.source.remote.response.MovieResponse
import com.ohmymovies.core.data.source.remote.response.MoviesResponse
import com.ohmymovies.core.data.source.remote.response.TvShowResponse
import com.ohmymovies.core.data.source.remote.response.TvshowsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi{
    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apiKey : String) : Response<MoviesResponse>

    @GET("tv/popular")
    suspend fun getTvshows(@Query("api_key") apiKey : String) : Response<TvshowsResponse>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("tv/{id}")
    suspend fun getDetailTvshow(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<TvShowResponse>
}