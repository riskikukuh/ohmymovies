package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonModel(
    val airDate: String? = "",
    val episodeCount: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    val overview: String? = "",
    val posterPath: String? = "",
    val seasonNumber: Int? = 0
) : Parcelable