package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatedByModel(
    val id: Int,
    val creditId: String? = "",
    val name: String? = "",
    val gender: Int? = 0,
    val profilePath: String? = ""
) : Parcelable