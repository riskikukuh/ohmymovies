package com.dicoding.ohmymovies.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "genres",
    primaryKeys = ["genreId"],
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["movieId"],
        childColumns = ["movieId"]
    ), ForeignKey(
        entity = TvshowEntity::class,
        parentColumns = ["tvshowId"],
        childColumns = ["tvshowId"]
    )],
    indices = [Index(value = ["genreId"]), Index(value = ["movieId"]), Index(value = ["tvshowId"])]
)
data class GenreEntity(
    @ColumnInfo(name = "genreId") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "tvshowId") val tvshowId: Int
)