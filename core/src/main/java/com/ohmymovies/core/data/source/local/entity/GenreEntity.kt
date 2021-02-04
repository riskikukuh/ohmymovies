package com.ohmymovies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    indices = [Index(value = ["genreId"]), Index(value = ["movieId"]), Index(value = ["tvshowId"])]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "genreId") val genreId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "tvshowId") val tvshowId: Int
)