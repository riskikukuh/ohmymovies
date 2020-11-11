package com.dicoding.ohmymovies.util

import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.data.model.Genre
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.TvShowModel

object FakeData {
    val MOVIE = MovieModel(
        posterImageResource = R.drawable.poster_alita,
        adult = false,
        backdropPath = "",
        belongsToCollection = null,
        genres = listOf(Genre(1, "genre")),
        homepage = "homepage1"
    )

    val TV_SHOW = TvShowModel(
        lastEpisodeToAir = null,
        nextEpisodeToAir = null,
        homepage = "homepage1",
        genres = listOf(Genre(1, "genre")),
        posterImageResource = R.drawable.poster_naruto_shipudden
    )
}