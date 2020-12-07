package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import com.dicoding.ohmymovies.data.model.entity.GenreEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
) : Parcelable {
    companion object {
        fun fromGenreEntity(data: GenreEntity): Genre = Genre(
            data.tvshowId, data.name
        )
    }
}