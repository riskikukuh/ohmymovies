package com.ohmymovies.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ohmymovies.core.data.source.local.entity.*

@Database(
    version = 1,
    entities = [MovieEntity::class, TvshowEntity::class, GenreEntity::class, LanguageEntity::class, SpokenLanguageEntity::class],
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoritesDao() : FavoritesDao

    companion object {
        private var INSTANCE: FavoritesDatabase? = null
        fun getDB(context: Context): FavoritesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            val db =
                Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favoritesDatabase"
                ).build()
            INSTANCE = db
            return db
        }
    }

}