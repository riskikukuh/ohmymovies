package com.dicoding.ohmymovies.data.source

import android.content.Context
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.source.remote.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultMovieRepository(
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return withContext(dispatcher) {
            return@withContext when (val result = remoteDataSource.getMovies()) {
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
        return@withContext when (val result = remoteDataSource.getTvshows()) {
            is Result.Success -> {
                Result.Success(result.data.results ?: emptyList())
            }
            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun getMovie(id : Int): Result<MovieModel> = withContext(dispatcher){
        return@withContext remoteDataSource.getMovie(id)
    }

    override suspend fun getTvshow(id : Int): Result<TvShowModel> = withContext(dispatcher){
        return@withContext remoteDataSource.getTvshow(id)
    }

    override suspend fun getFavoriteMovies(): Result<List<MovieEntity>> = withContext(dispatcher){
        return@withContext localDataSource.getFavoriteMovies()
    }

    override suspend fun getFavoriteTvshows(): Result<List<TvshowEntity>> = withContext(dispatcher){
        return@withContext localDataSource.getFavoriteTvshows()
    }

    override suspend fun getFavoriteMovie(context: Context, id: Int): Result<MovieEntity> = withContext(dispatcher){
        return@withContext localDataSource.getFavoriteMovie(context, id)
    }

    override suspend fun getFavoriteTvshow(context: Context, id: Int): Result<TvshowEntity> = withContext(dispatcher){
        return@withContext localDataSource.getFavoriteTvshow(context, id)
    }


}