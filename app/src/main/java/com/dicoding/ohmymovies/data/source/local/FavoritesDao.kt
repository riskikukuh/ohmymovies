package com.dicoding.ohmymovies.data.source.local

import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.ohmymovies.data.model.entity.*

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favoritesMovie")
    fun getAllFavoritesMovie(): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM favoritesMovie WHERE movieId = :id")
    suspend fun getMovie(id: Int): MovieWithGenreLanguage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM favoritesTvshow")
    fun getAllFavoritesTvshow(): DataSource.Factory<Int, TvshowEntity>

    @Transaction
    @Query("SELECT * FROM favoritesTvshow WHERE tvshowId = :id")
    suspend fun getTvshow(id: Int): TvshowWithGenreLanguage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvshows(vararg tvshow: TvshowEntity)

    @Delete
    suspend fun deleteTvshow(tvshow: TvshowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(vararg genre: GenreEntity)

    @Query("DELETE FROM genres WHERE genreId IN (:ids)")
    suspend fun deleteGenres(ids: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(vararg language: LanguageEntity)

    @Query("DELETE FROM languages WHERE id IN (:ids)")
    suspend fun deleteLanguages(ids: List<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpokenLanguage(vararg spokenLanguages: SpokenLanguageEntity)

    @Query("DELETE FROM spokenLanguage WHERE id IN (:ids)")
    suspend fun deleteSpokenLanguage(ids: List<Int>)


}