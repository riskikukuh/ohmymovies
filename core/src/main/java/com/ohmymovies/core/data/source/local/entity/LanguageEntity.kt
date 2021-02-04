package com.ohmymovies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "languages",
    indices = [Index(value = ["id"]), Index(value = ["tvshowId"])]
)
data class LanguageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "tvshowId") val tvshowId: Int,
    @ColumnInfo(name = "language") val name: String = ""
)