package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreatedByResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("credit_id") val creditId: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("gender") val gender: Int? = 0,
    @SerializedName("profile_path") val profilePath: String? = ""
)