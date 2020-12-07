package com.dicoding.ohmymovies.data.model

import android.os.Parcelable
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.SpokenLanguageEntity
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
    @SerializedName("vote_count") val voteCount: Int? = 0,
    var isFavorite: Boolean = false
) : Parcelable {
    fun getSpokenLanguageAsString() =
        spokenLanguages?.mapIndexed { index, item -> if (index == spokenLanguages.lastIndex && item.name.isNotEmpty()) item.name else item.name + ", " }
            ?.joinToString("")

    fun getRating() = voteAverage.toString()
    fun getAdult() = if (adult != null && adult) "Yes" else "No"

    companion object {
        fun fromMovieEntityWithGenre(data: MovieWithGenreLanguage): MovieModel = MovieModel(
            data.movie.adult,
            data.movie.backdropPath,
            null,
            data.movie.budget,
            data.genres.map { Genre.fromGenreEntity(it) },
            data.movie.homepage,
            data.movie.id,
            data.movie.imdbId,
            data.movie.originalLanguage,
            data.movie.originalTitle,
            data.movie.overview,
            data.movie.popularity,
            data.movie.posterPath,
            emptyList(),
            emptyList(),
            data.movie.releaseDate,
            data.movie.revenue,
            data.movie.runtime,
            data.spokenLanguage.map { SpokenLanguages.fromSpokenLanguageEntity(it) },
            data.movie.status,
            data.movie.tagline,
            data.movie.title,
            data.movie.video,
            data.movie.voteAverage,
            data.movie.voteCount,
            data.movie.isFavorite
        )

        fun fromMovieEntity(data: MovieEntity): MovieModel = MovieModel(
            data.adult,
            data.backdropPath,
            null,
            data.budget,
            emptyList(),
            data.homepage,
            data.id,
            data.imdbId,
            data.originalLanguage,
            data.originalTitle,
            data.overview,
            data.popularity,
            data.posterPath,
            emptyList(),
            emptyList(),
            data.releaseDate,
            data.revenue,
            data.runtime,
            emptyList(),
            data.status,
            data.tagline,
            data.title,
            data.video,
            data.voteAverage,
            data.voteCount,
            data.isFavorite
        )
    }
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
) : Parcelable {
    companion object {
        fun fromSpokenLanguageEntity(data: SpokenLanguageEntity): SpokenLanguages = SpokenLanguages(
            data.iso_639_1,
            data.name
        )
    }
}