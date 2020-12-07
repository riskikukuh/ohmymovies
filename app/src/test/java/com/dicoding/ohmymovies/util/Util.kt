package com.dicoding.ohmymovies.util

import com.dicoding.ohmymovies.data.model.BelongsToCollection
import com.dicoding.ohmymovies.data.model.EpisodeToAir
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel
import com.dicoding.ohmymovies.data.model.entity.MovieEntity
import com.dicoding.ohmymovies.data.model.entity.MovieWithGenreLanguage
import com.dicoding.ohmymovies.data.model.entity.TvshowEntity
import com.dicoding.ohmymovies.data.model.entity.TvshowWithGenreLanguage

object Util {
    private val fakeBelongsToCollection = BelongsToCollection(0, "", "", "")
    val fakeMovie = MovieModel(
        false,
        "",
        fakeBelongsToCollection,
        0,
        emptyList(),
        "Unknown",
        0,
        "",
        "",
        "",
        "",
        0.0,
        "",
        emptyList(),
        emptyList(),
        "",
        0,
        0,
        emptyList(),
        "",
        "",
        "",
        false,
        0.0,
        0
    )

    private val episodeToAir = EpisodeToAir(
        "", 0, 0, "", "", "", 0, 0, "", 0.0, 0
    )

    val fakeTvShow = TvShowModel(
        "",
        emptyList(),
        emptyList(),
        "",
        emptyList(),
        "Unknown",
        0,
        false,
        emptyList(),
        "",
        episodeToAir,
        "",
        episodeToAir,
        emptyList(),
        0,
        0,
        emptyList(),
        "",
        "",
        "",
        0.0,
        "",
        emptyList(),
        emptyList(),
        "",
        "",
        "",
        0.0,
        0
    )

    val exception = Exception("Test Exception")

    val fakeMovieEntity = MovieEntity(
        1, false, "", 0, "", "", "", "", "", 0.0, "", "", 0, 0, "", "", "", false, 0.0, 0
    )

    val fakeMovieWithGenreLanguage = MovieWithGenreLanguage(
        fakeMovieEntity, emptyList(), emptyList()
    )

    val fakeTvshowEntity = TvshowEntity(
        1, "", "", "", false, "", "", 0, 0, "", "", "", 0.0, "", "", "", "", 0.0, 0
    )

    val fakeTvshowWithGenreLanguage = TvshowWithGenreLanguage(
        fakeTvshowEntity, emptyList(), emptyList()
    )
}