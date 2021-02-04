package com.ohmymovies.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollectionResponse?,
    @SerializedName("budget") var budget: Int? = 0,
    @SerializedName("genres") val genres: List<GenreResponse>? = emptyList(),
    @SerializedName("homepage") var homepage: String = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("imdb_id") val imdbId: String? = "",
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyResponse>? = emptyList(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryResponse>? = emptyList(),
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("revenue") val revenue: Int? = 0,
    @SerializedName("runtime") val runtime: Int? = 0,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguagesResponse>? = emptyList(),
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0,
)