package com.ohmymovies.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MovieWithGenreLanguage(
    @Embedded
    var movie: MovieEntity,

    @Relation(
        parentColumn = "movieId",
        entity = SpokenLanguageEntity::class,
        entityColumn = "movieId"
    )
    var spokenLanguage: List<SpokenLanguageEntity>,

    @Relation(parentColumn = "movieId", entity = GenreEntity::class, entityColumn = "movieId")
    var genres: List<GenreEntity>
)