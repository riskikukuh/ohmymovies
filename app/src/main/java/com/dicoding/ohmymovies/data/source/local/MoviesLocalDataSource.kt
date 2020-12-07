package com.dicoding.ohmymovies.data.source.local

import androidx.paging.DataSource
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowWithGenreLanguage
import com.dicoding.ohmymovies.data.source.MovieLocalDataSource

class MoviesLocalDataSource(private val favoritesDao : FavoritesDao) : MovieLocalDataSource {

    override fun getFavoriteMovies(): Result<DataSource.Factory<Int, MovieEntity>> {
        return try {
            val response = favoritesDao.getAllFavoritesMovie()
            Success(response)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override fun getFavoriteTvshows(): Result<DataSource.Factory<Int, TvshowEntity>> {
        return try {
            val response = favoritesDao.getAllFavoritesTvshow()
            Success(response)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getFavoriteMovie(id: Int): Result<MovieWithGenreLanguage> {
        return try {
            val response = favoritesDao.getMovie(id)
            if (response != null) {
                Success(response)
            } else {
                Error(Exception("Movie Error"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getFavoriteTvshow(id: Int): Result<TvshowWithGenreLanguage> {
        return try {
            val response = favoritesDao.getTvshow(id)
            if (response != null) {
                Success(response)
            } else {
                Error(Exception("Tvshow Error"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun addMovieToFavorite(vararg movieEntity: MovieEntity): Result<Boolean> {
        return try {
            favoritesDao.insertMovies(*movieEntity)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun addTvshowToFavorite(vararg tvshow: TvshowEntity): Result<Boolean> {
        return try {
            favoritesDao.insertTvshows(*tvshow)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun deleteMovieFromFavorite(movie: MovieWithGenreLanguage): Result<Boolean> {
        return try {
            val listSpokenLanguageId = movie.spokenLanguage.map {
                it.id
            }
            val listGenreId = movie.genres.map {
                it.id
            }
            favoritesDao.deleteMovie(movie.movie)
            favoritesDao.deleteGenres(listGenreId)
            favoritesDao.deleteSpokenLanguage(listSpokenLanguageId)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun deleteTvshowFromFavorite(tvshow: TvshowWithGenreLanguage): Result<Boolean> {
        return try {
            val listLanguageId = tvshow.languages.map {
                it.id
            }
            val listGenreId = tvshow.genres.map {
                it.id
            }
            favoritesDao.deleteTvshow(tvshow.tvshow)
            favoritesDao.deleteGenres(listGenreId)
            favoritesDao.deleteLanguages(listLanguageId)
            Success(true)
        } catch (e: Exception) {
            Error(e)
        }
    }


}