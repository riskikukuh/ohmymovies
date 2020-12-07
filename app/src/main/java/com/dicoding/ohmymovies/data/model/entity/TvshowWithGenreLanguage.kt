package com.dicoding.ohmymovies.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvshowWithGenreLanguage(
    @Embedded
    var tvshow: TvshowEntity,

    @Relation(parentColumn = "tvshowId", entity = LanguageEntity::class, entityColumn = "tvshowId")
    var languages: List<LanguageEntity>,

    @Relation(parentColumn = "tvshowId", entityColumn = "genreId")
    var genres: List<GenreEntity>
) {
    fun episodeCountAsString() = tvshow.numberOfEpisodes.toString()
    fun getRating() = tvshow.voteAverage.toString()
    fun getLanguages() = languages.joinToString()
    fun getInProduction() =
        if (tvshow.inProduction != null && tvshow.inProduction!!) "Yes" else "No"
}
