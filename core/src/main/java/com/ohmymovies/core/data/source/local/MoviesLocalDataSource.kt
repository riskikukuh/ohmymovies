package com.ohmymovies.core.data.source.local

import androidx.paging.DataSource
import com.ohmymovies.core.data.Result
import com.ohmymovies.core.data.Result.Error
import com.ohmymovies.core.data.Result.Success
import com.ohmymovies.core.data.source.local.entity.*
import com.ohmymovies.core.data.source.local.room.FavoritesDao

class MoviesLocalDataSource(private val favoritesDao: FavoritesDao) {

    fun getFavoriteMovies(): Result<DataSource.Factory<Int, MovieEntity>> {
        return try {
            val response = favoritesDao.getAllFavoritesMovie()
            Success(response)
        } catch (e: Exception) {
            Error(e.message ?: "Movies Error")
        }
    }

    fun getFavoriteTvshows(): Result<DataSource.Factory<Int, TvshowEntity>> {
        return try {
            val response = favoritesDao.getAllFavoritesTvshow()
            Success(response)
        } catch (e: Exception) {
            Error(e.message ?: "Movies Error")
        }
    }

    suspend fun getFavoriteMovie(id: Int): Result<MovieWithGenreLanguage> {
        return try {
            val response = favoritesDao.getMovie(id)
            if (response != null) {
                Success(response)
            } else {
                Error("Movie Error")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Movie error")
        }
    }

    suspend fun getFavoriteTvshow(id: Int): Result<TvshowWithGenreLanguage> {
        return try {
            val response = favoritesDao.getTvshow(id)
            if (response != null) {
                Success(response)
            } else {
                Error("Tvshow Error")
            }
        } catch (e: Exception) {
            Error(e.message ?: "Tvshow error")
        }
    }

    suspend fun addSpokenLanguageToFavorite(vararg spokenLanguageEntity: SpokenLanguageEntity): Result<Boolean> {
        return try {
            favoritesDao.insertSpokenLanguage(*spokenLanguageEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error add genre to favorites")
        }
    }

    suspend fun addLanguageToFavorite(vararg languageEntity: LanguageEntity): Result<Boolean> {
        return try {
            favoritesDao.insertLanguage(*languageEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error add genre to favorites")
        }
    }

    suspend fun addGenreToFavorite(vararg genreEntity: GenreEntity): Result<Boolean> {
        return try {
            favoritesDao.insertGenres(*genreEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error add genre to favorites")
        }
    }

    suspend fun addMovieToFavorite(vararg movieEntity: MovieEntity): Result<Boolean> {
        return try {
            favoritesDao.insertMovies(*movieEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error add movie to favorites")
        }
    }

    suspend fun addTvshowToFavorite(vararg tvshowEntity: TvshowEntity): Result<Boolean> {
        return try {
            favoritesDao.insertTvshows(*tvshowEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error add Tvshow to favorites")
        }
    }

    suspend fun deleteMovieFromFavorite(movie: MovieWithGenreLanguage): Result<Boolean> {
        return try {
            val listSpokenLanguageId = movie.spokenLanguage.map {
                it.id
            }
            val listGenreId = movie.genres.map {
                it.genreId
            }
            favoritesDao.deleteMovie(movie.movie)
            favoritesDao.deleteGenres(listGenreId)
            favoritesDao.deleteSpokenLanguage(listSpokenLanguageId)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error delete movie from favorite")
        }
    }

    suspend fun deleteTvshowFromFavorite(movie: TvshowWithGenreLanguage): Result<Boolean> {
        return try {
            val listSpokenLanguageId = movie.languages.map {
                it.id
            }
            val listGenreId = movie.genres.map {
                it.genreId
            }
            favoritesDao.deleteTvshow(movie.tvshow)
            favoritesDao.deleteGenres(listGenreId)
            favoritesDao.deleteSpokenLanguage(listSpokenLanguageId)
            Success(true)
        } catch (e: Exception) {
            Error(e.message ?: "Error delete tvshow from favorite")
        }
    }

}