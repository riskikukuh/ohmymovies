package com.ohmymovies.core.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ohmymovies.core.R
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.Result.*
import com.ohmymovies.core.data.source.local.MoviesLocalDataSource
import com.ohmymovies.core.data.source.local.entity.*
import com.ohmymovies.core.data.source.remote.MovieRemoteDataSource
import com.ohmymovies.core.domain.model.MovieModel
import com.ohmymovies.core.domain.model.TvShowModel
import com.ohmymovies.core.domain.repository.IMoviesRepository
import com.ohmymovies.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class MoviesRepository(
    context: Context,
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val moviesRemoteDataSource: MovieRemoteDataSource,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : IMoviesRepository {

    private val errorMovieId = context.getString(R.string.movie_id_not_found)
    private val errorMovieEmpty = context.getString(R.string.movies_empty)
    private val errorTvshowId = context.getString(R.string.tvshow_id_not_found)
    private val errorTvshowEmpty = context.getString(R.string.tvshows_empty)

    private val unknownError = context.getString(R.string.unknown_error)

    private val favoriteMovies = MediatorLiveData<Result<PagedList<MovieEntity>>>()
    private val favoriteTvshows = MediatorLiveData<Result<PagedList<TvshowEntity>>>()

    override suspend fun getMovies(): Flow<Result<List<MovieModel>>> {
        return flow<Result<List<MovieModel>>> {
            emit(Loading(true))
            when (val response = moviesRemoteDataSource.getMovies()) {
                is Success -> {
                    emit(Loading(false))
                    val result = response.data.results?.map {
                        var isFavorite = false
                        if (it.id != null) {
                            when (moviesLocalDataSource.getFavoriteMovie(it.id)) {
                                is Success -> {
                                    isFavorite = true
                                }
                                else -> {
                                }
                            }
                        }
                        DataMapper.mapMovieResponseToMovieModel(it).copy(isFavorite = isFavorite)
                    }
                    emit(Success(result ?: emptyList()))
                }
                is Error -> {
                    emit(Loading(false))
                    emit(Error(response.message))
                }
            }
        }
    }

    override suspend fun getTvshows(): Flow<Result<List<TvShowModel>>> {
        return flow<Result<List<TvShowModel>>> {
            emit(Loading(true))
            when (val response = moviesRemoteDataSource.getTvshows()) {
                is Success -> {
                    emit(Loading(false))
                    val result = response.data.results?.map {
                        var isFavorite = false
                        if (it.id != null) {
                            when (moviesLocalDataSource.getFavoriteTvshow(it.id)) {
                                is Success -> {
                                    isFavorite = true
                                }
                                else -> {
                                }
                            }
                        }
                        DataMapper.mapTvshowResponseToTvshowModel(it).copy(isFavorite = isFavorite)
                    }
                    emit(Success(result ?: emptyList()))
                }
                is Error -> {
                    emit(Loading(false))
                    emit(Error(response.message))
                }
            }
        }
    }

    override suspend fun getMovie(id: Int): Flow<Result<MovieModel>> {
        return flow<Result<MovieModel>> {
            emit(Loading(true))
            when (val response = moviesRemoteDataSource.getMovie(id)) {
                is Success -> {
                    emit(Loading(false))
                    emit(Success(DataMapper.mapMovieResponseToMovieModel(response.data)))
                }
                is Error -> {
                    emit(Loading(false))
                    emit(Error(response.message))
                }
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getTvshow(id: Int): Flow<Result<TvShowModel>> {
        return flow<Result<TvShowModel>> {
            emit(Loading(true))
            when (val response = moviesRemoteDataSource.getTvshow(id)) {
                is Success -> {
                    emit(Loading(false))
                    emit(Success(DataMapper.mapTvshowResponseToTvshowModel(response.data)))
                }
                is Error -> {
                    emit(Loading(false))
                    emit(Error(response.message))
                }
            }
        }.flowOn(dispatcher)
    }

    override fun getFavoriteMovies(): LiveData<Result<PagedList<MovieEntity>>> {
        when (val response = moviesLocalDataSource.getFavoriteMovies()) {
            is Success -> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val live = LivePagedListBuilder(response.data, config).build()
                favoriteMovies.addSource(live) {
                    favoriteMovies.value = Success(it)
                }
            }
            is Error -> {
                favoriteMovies.value = Error(response.message)
            }
        }
        return favoriteMovies
    }

    override fun getFavoriteTvshows(): LiveData<Result<PagedList<TvshowEntity>>> {
        when (val response = moviesLocalDataSource.getFavoriteTvshows()) {
            is Success -> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                val live = LivePagedListBuilder(response.data, config).build()
                favoriteTvshows.addSource(live) {
                    favoriteTvshows.value = Success(it)
                }
            }
            is Error -> {
                favoriteTvshows.value = Error(response.message)
            }
        }
        return favoriteTvshows
    }

    override suspend fun getFavoriteMovie(id: Int): Result<MovieModel> =
        withContext(dispatcher) {
            return@withContext when (val response = moviesLocalDataSource.getFavoriteMovie(id)) {
                is Success -> {
                    Success(DataMapper.mapMovieWithGenreLanguageEntityToMovieModel(response.data))
                }
                is Error -> {
                    Error(response.message)
                }
                else -> {
                    Error(unknownError)
                }
            }
        }

    override suspend fun getFavoriteTvshow(id: Int): Result<TvShowModel> =
        withContext(dispatcher) {
            return@withContext when (val response = moviesLocalDataSource.getFavoriteTvshow(id)) {
                is Success -> {
                    Success(DataMapper.mapTvshowWithGenreLanguageEntityToTvshowModel(response.data))
                }
                is Error -> {
                    Error(response.message)
                }
                else -> {
                    Error(unknownError)
                }
            }
        }

    override suspend fun addMovieToFavorite(movieModel: MovieModel): Result<Boolean> =
        withContext(dispatcher) {
            val data = DataMapper.mapMovieModelToMovieEntity(movieModel)
            return@withContext when (val resultMovie =
                moviesLocalDataSource.addMovieToFavorite(data)) {
                is Success -> {
                    val listGenres = movieModel.genres?.map {
                        DataMapper.mapGenreModelToGenreEntity(
                            it,
                            movieModel.id,
                            0
                        )
                    } ?: emptyList()
                    when (val resultGenre =
                        moviesLocalDataSource.addGenreToFavorite(*listGenres.toTypedArray())) {
                        is Success -> {
                            val listSpokenLanguage = movieModel.spokenLanguages?.map {
                                DataMapper.mapSpokenLanguageModelToSpokenLanguageEntity(
                                    it,
                                    movieModel.id ?: 0
                                )
                            } ?: emptyList()
                            val resultSpokenLanguage =
                                moviesLocalDataSource.addSpokenLanguageToFavorite(*listSpokenLanguage.toTypedArray())
                            resultSpokenLanguage
                        }
                        is Error -> {
                            resultGenre
                        }
                        else -> Error(unknownError)
                    }
                }
                is Error -> {
                    Error(resultMovie.message)
                }
                else -> Error(unknownError)
            }
        }

    override suspend fun addTvshowToFavorite(tvShowModel: TvShowModel): Result<Boolean> =
        withContext(dispatcher) {
            val data = DataMapper.mapTvshowModelToTvshowEntity(tvShowModel)
            return@withContext when (val resultTvshow =
                moviesLocalDataSource.addTvshowToFavorite(data)) {
                is Success -> {
                    val listGenre = tvShowModel.genres?.map {
                        DataMapper.mapGenreModelToGenreEntity(
                            it,
                            0,
                            tvShowModel.id
                        )
                    } ?: emptyList()
                    when (val resultGenre =
                        moviesLocalDataSource.addGenreToFavorite(*listGenre.toTypedArray())) {
                        is Success -> {
                            val listLanguage = tvShowModel.languages?.map {
                                DataMapper.mapLanguageToLanguageEntity(
                                    it,
                                    tvShowModel.id ?: 0
                                )
                            } ?: emptyList()
                            val resultLanguage =
                                moviesLocalDataSource.addLanguageToFavorite(*listLanguage.toTypedArray())
                            resultLanguage
                        }
                        is Error -> {
                            resultGenre
                        }
                        else -> {
                            Error(unknownError)
                        }
                    }
                }
                is Error -> {
                    resultTvshow
                }
                else -> {
                    Error(unknownError)
                }
            }
        }

    override suspend fun removeFavoriteMovie(id: Int): Result<Boolean> =
        withContext(dispatcher) {
            return@withContext when (val response = moviesLocalDataSource.getFavoriteMovie(id)) {
                is Success -> {
                    moviesLocalDataSource.deleteMovieFromFavorite(response.data)
                }
                is Error -> {
                    Error(response.message)
                }
                else -> {
                    Error(unknownError)
                }
            }
        }

    override suspend fun removeFavoriteTvshow(id: Int): Result<Boolean> =
        withContext(dispatcher) {
            return@withContext when (val response = moviesLocalDataSource.getFavoriteTvshow(id)) {
                is Success -> {
                    moviesLocalDataSource.deleteTvshowFromFavorite(response.data)
                }
                is Error -> {
                    Error(response.message)
                }
                else -> {
                    Error(unknownError)
                }
            }
        }
}