package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BelongsToCollectionModel(
    val id: Int? = 0,
    val name: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = ""
) : Parcelable