package com.dicoding.ohmymovies.data.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.ohmymovies.data.model.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favoritesMovie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "belongs_to_collection") val belongsToCollection: BelongsToCollection?,
    @ColumnInfo(name = "budget") var budget: Int? = 0,
    @ColumnInfo(name = "genres") val genres: List<Genre>? = emptyList(),
    @ColumnInfo(name = "homepage") var homepage: String = "",
    @ColumnInfo(name = "imdb_id") val imdbId: String? = "",
    @ColumnInfo(name = "original_language") val originalLanguage: String? = "",
    @ColumnInfo(name = "original_title") val originalTitle: String? = "",
    @ColumnInfo(name = "overview") val overview: String? = "",
    @ColumnInfo(name = "popularity") val popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") val posterPath: String? = "",
    @ColumnInfo(name = "production_companies") val productionCompanies: List<ProductionCompany>? = emptyList(),
    @ColumnInfo(name = "production_countries") val productionCountries: List<ProductionCountry>? = emptyList(),
    @ColumnInfo(name = "release_date") val releaseDate: String? = "",
    @ColumnInfo(name = "revenue") val revenue: Int? = 0,
    @ColumnInfo(name = "runtime") val runtime: Int? = 0,
    @ColumnInfo(name = "spoken_languages") val spokenLanguages: List<SpokenLanguages>? = emptyList(),
    @ColumnInfo(name = "status") val status: String? = "",
    @ColumnInfo(name = "tagline") val tagline: String? = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "video") val video: Boolean? = false,
    @ColumnInfo(name = "vote_average") val voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") val voteCount: Int? = 0
) {
    fun getSpokenLanguageAsString() =
        spokenLanguages?.mapIndexed { index, item -> if (index == spokenLanguages.lastIndex && item.name.isNotEmpty()) item.name else item.name + ", " }
            ?.joinToString("")

    fun getRating() = voteAverage.toString()
    fun getAdult() = if (adult != null && adult) "Yes" else "No"
}