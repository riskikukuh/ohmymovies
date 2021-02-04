package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("name") val name: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("logo_path") val logoPath: String = "",
    @SerializedName("origin_country") val originCountry: String? = ""
)