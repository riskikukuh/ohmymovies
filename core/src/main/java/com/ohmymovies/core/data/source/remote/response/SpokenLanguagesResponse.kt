package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesResponse(
    @SerializedName("iso_639_1") val iso_639_1: String = "",
    @SerializedName("name") val name: String = ""
)