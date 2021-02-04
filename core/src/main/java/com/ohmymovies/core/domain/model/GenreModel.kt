package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreModel(
    val id: Int = 0,
    val name: String? = ""
) : Parcelable