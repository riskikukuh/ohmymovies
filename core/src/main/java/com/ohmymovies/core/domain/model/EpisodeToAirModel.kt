package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeToAirModel(
    val airDate: String? = "",
    val episodeNumber: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    val overview: String? = "",
    val productionCode: String? = "",
    val seasonNumber: Int? = 0,
    val showId: Int? = 0,
    val stillPath: String? = "",
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
) : Parcelable