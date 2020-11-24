package com.dicoding.ohmymovies.data.source

import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultMovieRepository(
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val dataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return withContext(dispatcher) {
            return@withContext when (val result = dataSource.getMovies()) {
                is Result.Success -> {
                    Result.Success(result.data.results ?: emptyList())
                }
                is Result.Error -> {
                    result
                }
            }
        }

    }

    override suspend fun getTvShows(): Result<List<TvShowModel>> = withContext(dispatcher) {
        return@withContext when (val result = dataSource.getTvshows()) {
            is Result.Success -> {
                Result.Success(result.data.results ?: emptyList())
            }
            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun getMovie(id : Int): Result<MovieModel> = withContext(dispatcher){
        return@withContext dataSource.getMovie(id)
    }

    override suspend fun getTvshow(id : Int): Result<TvShowModel> = withContext(dispatcher){
        return@withContext dataSource.getTvshow(id)
    }
}