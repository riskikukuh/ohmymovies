package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpokenLanguagesModel(
    val iso_639_1: String? = "",
    val name: String? = ""
) : Parcelable