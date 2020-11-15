package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel

class DefaultMovieRepository(
    private val dataSource: MovieDataSource
) : MovieRepository {

    private val movies = mutableListOf<MovieModel>()
    private val tvShows = mutableListOf<TvShowModel>()

    override suspend fun getMovies(
        update: Boolean,
        context: Context
    ): Result<List<MovieModel>> {
        if (update) {
            val result = dataSource.getMovies(context)
            if (result is Result.Success) {
                with(movies) {
                    clear()
                    addAll(result.data)
                }
            } else {
                return result
            }
        }

        return Result.Success(movies)
    }

    override suspend fun getTvShows(update: Boolean, context: Context): Result<List<TvShowModel>> {
        if (update) {
            val result = dataSource.getTvshows(context)
            if (result is Result.Success) {
                with(tvShows) {
                    clear()
                    addAll(result.data)
                }
            } else {
                return result
            }
        }

        return Result.Success(tvShows)
    }


}