package com.dicoding.ohmymovies.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favoritesMovie")
    suspend fun getAllFavoritesMovie() : List<MovieEntity>

    @Query("SELECT * FROM favoritesMovie WHERE id = :id")
    suspend fun getMovie(id : Int) : MovieEntity?

    @Insert
    suspend fun insertAllMovies(vararg users : MovieEntity)

    @Delete
    suspend fun deleteMovie(user : MovieEntity)

    @Query("SELECT * FROM favoritesTvshow")
    suspend fun getAllFavoritesTvshow() : List<TvshowEntity>

    @Query("SELECT * FROM favoritesTvshow WHERE id = :id")
    suspend fun getTvshow(id : Int) : TvshowEntity?

    @Insert
    suspend fun insertAllTvshows(vararg users : TvshowEntity)

    @Delete
    suspend fun deleteTvshow(user : TvshowEntity)



//    @Query("SELECT * FROM favoriteUsers")
//    fun findAll() : Cursor
//
//    @Query("SELECT * FROM favoriteUsers WHERE id = :id")
//    fun findUser(id : Int) : Cursor

}