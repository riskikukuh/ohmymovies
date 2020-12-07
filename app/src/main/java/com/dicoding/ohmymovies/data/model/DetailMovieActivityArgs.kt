package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovieActivityArgs(
    val id: Int? = 0,
    val title: String? = "",
    val isOpenFromFavorite: Boolean? = false,
    val isFavorite: Boolean? = false
) : Parcelable