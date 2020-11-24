package com.dicoding.ohmymovies.util

import com.dicoding.ohmymovies.data.model.Genre
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.data.model.SpokenLanguages
import com.dicoding.ohmymovies.data.model.TvShowModel

object FakeData {
    val MOVIE = MovieModel(
        id = 528085,
        adult = false,
        voteAverage = 4.8,
        releaseDate = "2020-10-01",
        backdropPath = "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
        posterPath = "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
        overview = "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
        belongsToCollection = null,
        spokenLanguages = listOf(SpokenLanguages(name = "English")),
        genres = listOf(Genre(1, "genre")),
        homepage = "http://screen.nsw.gov.au/project/2067"
    )

    val TV_SHOW = TvShowModel(
        id = 82856,
        lastEpisodeToAir = null,
        nextEpisodeToAir = null,
        voteAverage = 8.5,
        numberOfEpisodes = 16,
        languages = listOf("en"),
        status = "Returning Series",
        backdropPath = "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
        posterPath = "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
        overview = "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
        homepage = "https://www.disneyplus.com/series/the-mandalorian/3jLIGMDYINqD",
        genres = listOf(Genre(1, "genre")),
    )
}