package com.dicoding.ohmymovies.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity

@Database(version = 1, entities = [MovieEntity::class, TvshowEntity::class], exportSchema = false)
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
