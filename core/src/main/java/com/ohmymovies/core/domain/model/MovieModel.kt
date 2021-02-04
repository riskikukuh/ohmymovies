package com.ohmymovies.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollectionModel?,
    var budget: Int? = 0,
    val genres: List<GenreModel>? = emptyList(),
    var homepage: String? = "",
    val id: Int? = 0,
    val imdbId: String? = "",
    val originalLanguage: String? = "",
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val posterPath: String? = "",
    val productionCompanies: List<ProductionCompanyModel>? = emptyList(),
    val productionCountries: List<ProductionCountryModel>? = emptyList(),
    val releaseDate: String? = "",
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val spokenLanguages: List<SpokenLanguagesModel>? = emptyList(),
    val status: String? = "",
    val tagline: String? = "",
    val title: String = "",
    val video: Boolean? = false,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
    var isFavorite: Boolean = false
) : Parcelable {
    fun getSpokenLanguageAsString() =
        spokenLanguages?.mapIndexed { index, item -> if (index == spokenLanguages.lastIndex && (item.name == null || item.name.isEmpty())) item.name else item.name + ", " }
            ?.joinToString("")

    fun getRating() = voteAverage.toString()
    fun getAdultAsString() = if (adult != null && adult) "Yes" else "No"
}