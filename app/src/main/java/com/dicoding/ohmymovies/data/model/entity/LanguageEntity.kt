package com.dicoding.ohmymovies.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "languages",
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = TvshowEntity::class,
        parentColumns = ["tvshowId"],
        childColumns = ["tvshowId"]
    )],
    indices = [Index(value = ["id"]), Index(value = ["tvshowId"])]
)
data class LanguageEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "tvshowId") val tvshowId: Int,
    @ColumnInfo(name = "language") val name: String = ""
)