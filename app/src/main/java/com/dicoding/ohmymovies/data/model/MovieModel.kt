package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget") var budget: Int? = 0,
    @SerializedName("genres") val genres: List<Genre>? = emptyList(),
    @SerializedName("homepage") var homepage: String = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("imdb_id") val imdbId: String? = "",
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    var posterImageResource: Int = 0,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>? = emptyList(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>? = emptyList(),
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("revenue") val revenue: Int? = 0,
    @SerializedName("runtime") val runtime: Int? = 0,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguages>? = emptyList(),
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0
) : Parcelable {
    fun getSpokenLanguageAsString() =
        spokenLanguages?.mapIndexed { index, item -> if (index == spokenLanguages.lastIndex && item.name.isNotEmpty()) item.name else item.name + ", " }
            ?.joinToString("")

    fun getRating() = voteAverage.toString()
    fun getAdult() = if (adult != null && adult) "Yes" else "No"
}

@Parcelize
data class BelongsToCollection(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String
) : Parcelable

@Parcelize
data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso_3166_1: String,
    @SerializedName("name") val name: String
) : Parcelable

@Parcelize
data class SpokenLanguages(
    @SerializedName("iso_639_1") val iso_639_1: String = "",
    @SerializedName("name") val name: String = ""
) : Parcelable