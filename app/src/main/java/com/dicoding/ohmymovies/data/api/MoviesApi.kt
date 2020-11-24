package com.dicoding.ohmymovies.data.api

import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.MoviesResponse
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.TvshowsResponse
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
    suspend fun getDetailMovie(@Path("id") id : Int, @Query("api_key") apiKey : String) : Response<MovieModel>

    @GET("tv/{id}")
    suspend fun getDetailTvshow(@Path("id") id : Int, @Query("api_key") apiKey : String) : Response<TvShowModel>
}