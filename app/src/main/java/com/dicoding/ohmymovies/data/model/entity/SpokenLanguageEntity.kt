package com.dicoding.ohmymovies.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "spokenLanguage",
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = MovieEntity::class,
        parentColumns = ["movieId"],
        childColumns = ["movieId"]
    )],
    indices = [Index(value = ["id"]), Index(value = ["movieId"])]
)
data class SpokenLanguageEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "movieId") var movieId: String = "",
    @ColumnInfo(name = "iso_639_1") val iso_639_1: String = "",
    @ColumnInfo(name = "name") val name: String = ""
)