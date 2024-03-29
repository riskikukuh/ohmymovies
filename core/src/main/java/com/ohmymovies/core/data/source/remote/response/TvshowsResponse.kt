package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvshowsResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("results")
    val results: List<TvShowResponse>? = emptyList()
)