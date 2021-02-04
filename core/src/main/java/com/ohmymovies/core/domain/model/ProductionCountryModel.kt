package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCountryModel(
    val iso_3166_1: String? = "",
    val name: String? = ""
) : Parcelable