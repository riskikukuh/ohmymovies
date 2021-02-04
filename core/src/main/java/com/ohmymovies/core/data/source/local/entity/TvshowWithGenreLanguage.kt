package com.ohmymovies.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvshowWithGenreLanguage(
    @Embedded
    var tvshow: TvshowEntity,

    @Relation(parentColumn = "tvshowId", entity = LanguageEntity::class, entityColumn = "tvshowId")
    var languages: List<LanguageEntity>,

    @Relation(parentColumn = "tvshowId", entityColumn = "tvshowId")
    var genres: List<GenreEntity>
)