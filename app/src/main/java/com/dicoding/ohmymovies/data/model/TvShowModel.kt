package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowModel(
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("created_by") val createdBy: List<CreatedBy>? = emptyList(),
    @SerializedName("episode_run_time") val episodeRunTime: List<Int> = emptyList(),
    @SerializedName("first_air_date") val firstAirDate: String? = "",
    @SerializedName("genres") val genres: List<Genre>? = emptyList(),
    @SerializedName("homepage") var homepage: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("in_production") val inProduction: Boolean? = false,
    @SerializedName("languages") val languages: List<String>? = emptyList(),
    @SerializedName("last_air_date") val lastAirDate: String? = "",
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: EpisodeToAir?,
    @SerializedName("name") val name: String = "",
    @SerializedName("next_episode_to_air") val nextEpisodeToAir: EpisodeToAir?,
    @SerializedName("networks") val networks: List<Network> = emptyList(),
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int? = 0,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int? = 0,
    @SerializedName("origin_country") val originCountry: List<String>? = emptyList(),
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_name") val originalName: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>? = emptyList(),
    @SerializedName("seasons") val seasons: List<Season>? = emptyList(),
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline : String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0
) : Parcelable {
    fun episodeCountAsString() = numberOfEpisodes.toString()
    fun getRating() = voteAverage.toString()
    fun getLanguages() = languages?.joinToString()
    fun getInProduction() = if (inProduction != null && inProduction) "Yes" else "No"
}

@Parcelize
data class CreatedBy(
    @SerializedName("id") val id : Int,
    @SerializedName("credit_id") val creditId : String? = "",
    @SerializedName("name") val name : String? = "",
    @SerializedName("gender") val gender : Int? = 0,
    @SerializedName("profile_path") val profilePath : String? = ""
) : Parcelable

@Parcelize
data class Network(
    @SerializedName("name") val name : String? = "",
    @SerializedName("id") val id : Int? = 0,
    @SerializedName("logo_path") val logoPath : String = "",
    @SerializedName("origin_country") val originCountry : String? = ""
) : Parcelable

@Parcelize
data class Season(
    @SerializedName("air_date") val airDate : String? = "",
    @SerializedName("episode_count") val episodeCount : Int? = 0,
    @SerializedName("id") val id : Int? = 0,
    @SerializedName("name") val name : String? = "",
    @SerializedName("overview") val overview : String? = "",
    @SerializedName("poster_path") val posterPath : String? = "",
    @SerializedName("season_number") val seasonNumber : Int? = 0
) : Parcelable

@Parcelize
data class EpisodeToAir(
    @SerializedName("air_date") val airDate : String? = "",
    @SerializedName("episode_number") val episodeNumber : Int? = 0,
    @SerializedName("id") val id : Int? = 0,
    @SerializedName("name") val name : String? = "",
    @SerializedName("overview") val overview : String? = "",
    @SerializedName("production_code") val productionCode : String? = "",
    @SerializedName("season_number") val seasonNumber : Int? = 0,
    @SerializedName("show_id") val showId : Int? = 0,
    @SerializedName("still_path") val stillPath : String? = "",
    @SerializedName("vote_average") val voteAverage : Double? = 0.0,
    @SerializedName("vote_count") val voteCount : Int? = 0
) : Parcelable
