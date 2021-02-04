package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkModel(
    val name: String? = "",
    val id: Int? = 0,
    val logoPath: String? = "",
    val originCountry: String? = ""
) : Parcelable