package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowModel(
    val backdropPath: String? = "",
    val createdBy: List<CreatedByModel>? = emptyList(),
    val episodeRunTime: List<Int>? = emptyList(),
    val firstAirDate: String? = "",
    val genres: List<GenreModel>? = emptyList(),
    var homepage: String? = "",
    val id: Int? = 0,
    val inProduction: Boolean? = false,
    val languages: List<String>? = emptyList(),
    val lastAirDate: String? = "",
    val lastEpisodeToAir: EpisodeToAirModel?,
    val name: String? = "",
    val nextEpisodeToAir: EpisodeToAirModel?,
    val networks: List<NetworkModel>? = emptyList(),
    val numberOfEpisodes: Int? = 0,
    val numberOfSeasons: Int? = 0,
    val originCountry: List<String>? = emptyList(),
    val originalLanguage: String? = "",
    val originalName: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val posterPath: String? = "",
    val productionCompanies: List<ProductionCompanyModel>? = emptyList(),
    val seasons: List<SeasonModel>? = emptyList(),
    val status: String? = "",
    val tagline: String? = "",
    val type: String? = "",
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
    var isFavorite: Boolean = false
) : Parcelable {
    fun episodeCountAsString() = numberOfEpisodes.toString()
    fun getRating() = voteAverage.toString()
    fun getLanguagesAsString() = this.languages?.joinToString() ?: "Unknown"
    fun getInProductionAsString() = if (this.inProduction != null && this.inProduction) "Yes" else "No"
}