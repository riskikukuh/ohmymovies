package com.ohmymovies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritesMovie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int?,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "budget") var budget: Int? = 0,
    @ColumnInfo(name = "homepage") var homepage: String = "",
    @ColumnInfo(name = "imdb_id") val imdbId: String? = "",
    @ColumnInfo(name = "original_language") val originalLanguage: String? = "",
    @ColumnInfo(name = "original_title") val originalTitle: String? = "",
    @ColumnInfo(name = "overview") val overview: String? = "",
    @ColumnInfo(name = "popularity") val popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = "",
    @ColumnInfo(name = "release_date") val releaseDate: String? = "",
    @ColumnInfo(name = "revenue") val revenue: Int? = 0,
    @ColumnInfo(name = "runtime") val runtime: Int? = 0,
    @ColumnInfo(name = "status") val status: String? = "",
    @ColumnInfo(name = "tagline") val tagline: String? = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "video") val video: Boolean? = false,
    @ColumnInfo(name = "vote_average") val voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int? = 0,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = true
)