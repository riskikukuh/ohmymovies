package com.ohmymovies.core.ui.args

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTvshowActivityArgs(
    val id: Int? = 0,
    val title: String? = "",
    val isOpenFromFavorite: Boolean? = false,
    val isFavorite: Boolean? = false,
) : Parcelable