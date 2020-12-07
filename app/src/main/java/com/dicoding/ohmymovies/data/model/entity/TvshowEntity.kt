package com.dicoding.ohmymovies.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.ohmymovies.data.model.TvShowModel

@Entity(tableName = "favoritesTvshow")
data class TvshowEntity(
    @PrimaryKey
    @ColumnInfo(name = "tvshowId")
    val id: Int?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String? = "",
    @ColumnInfo(name = "first_air_date") val firstAirDate: String? = "",
    @ColumnInfo(name = "homepage") var homepage: String? = "",
    @ColumnInfo(name = "in_production") val inProduction: Boolean? = false,
    @ColumnInfo(name = "last_air_date") val lastAirDate: String? = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "number_of_episodes") val numberOfEpisodes: Int? = 0,
    @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int? = 0,
    @ColumnInfo(name = "original_language") val originalLanguage: String? = "",
    @ColumnInfo(name = "original_name") val originalName: String? = "",
    @ColumnInfo(name = "overview") val overview: String? = "",
    @ColumnInfo(name = "popularity") val popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = "",
    @ColumnInfo(name = "status") val status: String? = "",
    @ColumnInfo(name = "tagline") val tagline: String? = "",
    @ColumnInfo(name = "type") val type: String? = "",
    @ColumnInfo(name = "vote_average") val voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int? = 0,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = true
) {
    companion object {
        fun fromTvshowModel(data: TvShowModel): TvshowEntity = TvshowEntity(
            data.id,
            data.backdropPath,
            data.firstAirDate,
            data.homepage,
            data.inProduction,
            data.lastAirDate,
            data.name,
            data.numberOfEpisodes,
            data.numberOfSeasons,
            data.originalLanguage,
            data.originalName,
            data.overview,
            data.popularity,
            data.posterPath,
            data.status,
            data.tagline,
            data.type,
            data.voteAverage,
            data.voteCount
        )
    }
}
