package com.dicoding.ohmymovies.data.source.local

import android.content.Context
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.Result
import com.dicoding.ohmymovies.data.Result.Success
import com.dicoding.ohmymovies.data.Result.Error
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.source.MovieLocalDataSource

class MoviesLocalDataSource(private val favoritesDao : FavoritesDao) : MovieLocalDataSource {
    override suspend fun getFavoriteMovies(): Result<List<MovieEntity>> {
        return try {
            val response = favoritesDao.getAllFavoritesMovie()
            Success(response)
        }catch (e : Exception){
            Error(e)
        }
    }

    override suspend fun getFavoriteTvshows(): Result<List<TvshowEntity>> {
        return try {
            val response =favoritesDao.getAllFavoritesTvshow()
            Success(response)
        }catch (e :Exception){
            Error(e)
        }
    }

    override suspend fun getFavoriteMovie(context : Context, id: Int): Result<MovieEntity> {
        if (id <= 0){
            return Error(Exception(context.getString(R.string.movie_id_not_found)))
        }
        return try {
            val response = favoritesDao.getMovie(id)
            if (response != null){
                Success(response)
            }else{
                Error(Exception("Movie Error"))
            }
        }catch (e : Exception){
            Error(e)
        }
    }

    override suspend fun getFavoriteTvshow(context : Context, id: Int): Result<TvshowEntity> {
        if (id <= 0){
            return Error(Exception(context.getString(R.string.tvshow_id_not_found)))
        }
        return try {
            val response = favoritesDao.getTvshow(id)
            if (response != null){
                Success(response)
            }else{
                Error(Exception("Tvshow Error"))
            }
        }catch (e : Exception){
            Error(e)
        }
    }

}