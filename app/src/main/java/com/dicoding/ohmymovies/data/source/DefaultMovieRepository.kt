package com.dicoding.ohmymovies.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowWithGenreLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultMovieRepository(
    context: Context,
    private val dispatcher: CoroutineContext = Dispatchers.IO,
    private val remoteDataSource: MovieDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    private val errorMovieId = context.getString(R.string.movie_id_not_found)
    private val errorMovieEmpty = context.getString(R.string.movies_empty)
    private val errorTvshowId = context.getString(R.string.tvshow_id_not_found)
    private val errorTvshowEmpty = context.getString(R.string.tvshows_empty)

    private val favoriteMovies = MediatorLiveData<Result<PagedList<MovieEntity>>>()
    private val favoriteTvshows = MediatorLiveData<Result<PagedList<TvshowEntity>>>()

    override suspend fun getMovies(): Result<List<MovieModel>> {
        return withContext(dispatcher) {
            return@withContext when (val result = remoteDataSource.getMovies()) {
                is Result.Success -> {
                    val response = result.data.results ?: emptyList()
                    val finalResult = response.map {
                        when (val temp = localDataSource.getFavoriteMovie(it.id ?: 0)) {
                            is Result.Success -> {
                                it.apply {
                                    isFavorite = true
                                }
                            }
                            is Result.Error -> {
                                it.apply {
                                    isFavorite = false
                                }
                            }
                        }
                    }
                    Result.Success(finalResult)
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
                val response = result.data.results ?: emptyList()
                val finalResult = response.map {
                    when (val temp = localDataSource.getFavoriteTvshow(it.id ?: 0)) {
                        is Result.Success -> {
                            it.apply {
                                isFavorite = true
                            }
                        }
                        is Result.Error -> {
                            it.apply {
                                isFavorite = false
                            }
                        }
                    }
                }
                Result.Success(finalResult)
            }
            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun getMovie(id : Int): Result<MovieModel> = withContext(dispatcher) {
        if (id <= 0) {
            return@withContext Result.Error(Exception(errorMovieId))
        }
        return@withContext remoteDataSource.getMovie(id)
    }

    override suspend fun getTvshow(id: Int): Result<TvShowModel> = withContext(dispatcher) {
        if (id <= 0) {
            return@withContext Result.Error(Exception(errorTvshowId))
        }
        return@withContext remoteDataSource.getTvshow(id)
    }

    override fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>> {
        when (val response = localDataSource.getFavoriteMovies()) {
            is Result.Success -> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val live = LivePagedListBuilder(response.data, config).build()
                favoriteMovies.addSource(live) {
                    favoriteMovies.value = Result.Success(it)
                }
            }
            is Result.Error -> {
                favoriteMovies.value = response
            }
        }
        return favoriteMovies
    }

    override fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>> {
        when (val response = localDataSource.getFavoriteTvshows()) {
            is Result.Success -> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val live = LivePagedListBuilder(response.data, config).build()
                favoriteTvshows.addSource(live) {
                    favoriteTvshows.value = Result.Success(it)
                }
            }
            is Result.Error -> {
                favoriteTvshows.value = response
            }
        }
        return favoriteTvshows
    }

    override suspend fun getFavoriteMovie(id: Int): Result<MovieWithGenreLanguage> =
        withContext(dispatcher) {
            return@withContext localDataSource.getFavoriteMovie(id)
        }

    override suspend fun getFavoriteTvshow(id: Int): Result<TvshowWithGenreLanguage> =
        withContext(dispatcher) {
            return@withContext localDataSource.getFavoriteTvshow(id)
        }

    override suspend fun addMovieToFavorite(movie: MovieModel): Result<Boolean> =
        withContext(dispatcher) {
            val movieEntity = MovieEntity.fromMovieModel(movie)
            return@withContext localDataSource.addMovieToFavorite(movieEntity)
        }

    override suspend fun addTvshowToFavorite(tvshow: TvShowModel): Result<Boolean> =
        withContext(dispatcher) {
            val tvshowEntity = TvshowEntity.fromTvshowModel(tvshow)
            return@withContext localDataSource.addTvshowToFavorite(tvshowEntity)
        }

    override suspend fun removeMovieFromFavorite(id: Int): Result<Boolean> =
        withContext(dispatcher) {
            return@withContext when (val result = localDataSource.getFavoriteMovie(id)) {
                is Result.Success -> {
                    localDataSource.deleteMovieFromFavorite(result.data)
                }
                is Result.Error -> {
                    result
                }
            }
        }

    override suspend fun removeTvshowFromFavorite(id: Int): Result<Boolean> =
        withContext(dispatcher) {
            return@withContext when (val result = localDataSource.getFavoriteTvshow(id)) {
                is Result.Success -> {
                    localDataSource.deleteTvshowFromFavorite(result.data)
                }
                is Result.Error -> {
                    result
                }
            }
        }


}