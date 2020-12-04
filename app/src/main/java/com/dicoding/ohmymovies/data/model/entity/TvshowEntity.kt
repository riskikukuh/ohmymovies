package com.dicoding.ohmymovies.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.ohmymovies.data.model.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favoritesTvshow")
data class TvshowEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String? = "",
    @ColumnInfo(name = "created_by") val createdBy: List<CreatedBy>? = emptyList(),
    @ColumnInfo(name = "episode_run_time") val episodeRunTime: List<Int> = emptyList(),
    @ColumnInfo(name = "first_air_date") val firstAirDate: String? = "",
    @ColumnInfo(name = "genres") val genres: List<Genre>? = emptyList(),
    @ColumnInfo(name = "homepage") var homepage: String? = "",
    @ColumnInfo(name = "in_production") val inProduction: Boolean? = false,
    @ColumnInfo(name = "languages") val languages: List<String>? = emptyList(),
    @ColumnInfo(name = "last_air_date") val lastAirDate: String? = "",
    @ColumnInfo(name = "last_episode_to_air") val lastEpisodeToAir: EpisodeToAir?,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "next_episode_to_air") val nextEpisodeToAir: EpisodeToAir?,
    @ColumnInfo(name = "networks") val networks: List<Network> = emptyList(),
    @ColumnInfo(name = "number_of_episodes") val numberOfEpisodes: Int? = 0,
    @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int? = 0,
    @ColumnInfo(name = "origin_country") val originCountry: List<String>? = emptyList(),
    @ColumnInfo(name = "original_language") val originalLanguage: String? = "",
    @ColumnInfo(name = "original_name") val originalName: String? = "",
    @ColumnInfo(name = "overview") val overview: String? = "",
    @ColumnInfo(name = "popularity") val popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = "",
    @ColumnInfo(name = "production_companies") val productionCompanies: List<ProductionCompany>? = emptyList(),
    @ColumnInfo(name = "seasons") val seasons: List<Season>? = emptyList(),
    @ColumnInfo(name = "status") val status: String? = "",
    @ColumnInfo(name = "tagline") val tagline: String? = "",
    @ColumnInfo(name = "type") val type: String? = "",
    @ColumnInfo(name = "vote_average") val voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int? = 0
) {
    fun episodeCountAsString() = numberOfEpisodes.toString()
    fun getRating() = voteAverage.toString()
    fun getLanguages() = languages?.joinToString()
    fun getInProduction() = if (inProduction != null && inProduction) "Yes" else "No"
}
