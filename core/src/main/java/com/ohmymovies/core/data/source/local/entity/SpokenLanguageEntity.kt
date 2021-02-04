package com.ohmymovies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "spokenLanguage",
    indices = [Index(value = ["id"]), Index(value = ["movieId"])]
)
data class SpokenLanguageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movieId") var movieId: Int = 0,
    @ColumnInfo(name = "iso_639_1") val iso_639_1: String = "",
    @ColumnInfo(name = "name") val name: String = ""
)