package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.model.TvShowModel

class FakeMovieRepository(private val movies : List<MovieModel>, private val tvShow : List<TvShowModel>, private val dataSource: MovieDataSource) : MovieRepository {

    private var returnError = false

    fun setReturnError(data : Boolean) {
        returnError = data
    }

    companion object{
        val TEST_EXCEPTION = Exception("Test Exception")
    }

    override suspend fun getMovies(update: Boolean, context: Context): Result<List<MovieModel>> {
        if (returnError) return Error(TEST_EXCEPTION)

        return Success(movies)
    }

    override suspend fun getTvShows(update: Boolean, context: Context): Result<List<TvShowModel>> {
        if (returnError) return Error(TEST_EXCEPTION)
        return Success(tvShow)
    }

}