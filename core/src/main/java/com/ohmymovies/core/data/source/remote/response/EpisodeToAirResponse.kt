package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class EpisodeToAirResponse(
    @SerializedName("air_date") val airDate: String? = "",
    @SerializedName("episode_number") val episodeNumber: Int? = 0,
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("production_code") val productionCode: String? = "",
    @SerializedName("season_number") val seasonNumber: Int? = 0,
    @SerializedName("show_id") val showId: Int? = 0,
    @SerializedName("still_path") val stillPath: String? = "",
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0
)