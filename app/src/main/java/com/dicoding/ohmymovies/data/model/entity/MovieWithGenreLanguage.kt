package com.dicoding.ohmymovies.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MovieWithGenreLanguage(
    @Embedded
    var movie: MovieEntity,

    @Relation(parentColumn = "movieId", entity = SpokenLanguageEntity::class, entityColumn = "id")
    var spokenLanguage: List<SpokenLanguageEntity>,

    @Relation(parentColumn = "movieId", entity = GenreEntity::class, entityColumn = "genreId")
    var genres: List<GenreEntity>
) {
    fun getSpokenLanguageAsString() =
        spokenLanguage.mapIndexed { index, item -> if (index == spokenLanguage.lastIndex && item.name.isNotEmpty()) item.name else item.name + ", " }
            .joinToString("")

    fun getRating() = movie.voteAverage.toString()
    fun getAdult() = if (movie.adult != null && movie.adult!!) "Yes" else "No"
}