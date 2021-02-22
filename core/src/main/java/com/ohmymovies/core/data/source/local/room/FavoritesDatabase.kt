package com.ohmymovies.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ohmymovies.core.data.source.local.entity.*

@Database(
    version = 1,
    entities = [MovieEntity::class, TvshowEntity::class, GenreEntity::class, LanguageEntity::class, SpokenLanguageEntity::class],
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoritesDao() : FavoritesDao

}