package com.ohmymovies.core.utils

import com.ohmymovies.core.data.source.local.entity.*
import com.ohmymovies.core.data.source.remote.response.*
import com.ohmymovies.core.domain.model.*

object DataMapper {

    fun mapBelongsToCollectionResponseToBelongsToCollectionModel(data: BelongsToCollectionResponse?): BelongsToCollectionModel =
        BelongsToCollectionModel(
            data?.id,
            data?.name,
            data?.posterPath,
            data?.backdropPath
        )

    fun mapMovieResponseToMovieModel(data: MovieResponse): MovieModel = MovieModel(
        data.adult,
        data.backdropPath,
        mapBelongsToCollectionResponseToBelongsToCollectionModel(data.belongsToCollection),
        data.budget,
        data.genres?.map { mapGenreResponseToGenreModel(it) },
        data.homepage,
        data.id,
        data.imdbId,
        data.originalLanguage,
        data.originalTitle,
        data.overview,
        data.popularity,
        data.posterPath,
        data.productionCompanies?.map { mapProductionCompanyResponseToProductionCompanyModel(it) },
        data.productionCountries?.map { mapProductionCountryResponseToProducionCountryModel(it) },
        data.releaseDate,
        data.revenue,
        data.runtime,
        data.spokenLanguages?.map { mapSpokenLanguageResponseToSpokenLanguageModel(it) },
        data.status,
        data.tagline,
        data.title,
        data.video,
        data.voteAverage,
        data.voteCount,
        false
    )

    fun mapMovieWithGenreLanguageEntityToMovieModel(data: MovieWithGenreLanguage): MovieModel =
        MovieModel(
            data.movie.adult,
            data.movie.backdropPath,
            null,
            data.movie.budget,
            data.genres.map { mapGenreEntityToGenreModel(it) },
            data.movie.homepage,
            data.movie.id,
            data.movie.imdbId,
            data.movie.originalLanguage,
            data.movie.originalTitle,
            data.movie.overview,
            data.movie.popularity,
            data.movie.posterPath,
            null,
            null,
            data.movie.releaseDate,
            data.movie.revenue,
            data.movie.runtime,
            data.spokenLanguage.map { mapSpokenLanguageEntityToSpokenLanguageModel(it) },
            data.movie.status,
            data.movie.tagline,
            data.movie.title,
            data.movie.video,
            data.movie.voteAverage,
            data.movie.voteCount,
            false
        )

    fun mapProductionCompanyResponseToProductionCompanyModel(data: ProductionCompanyResponse): ProductionCompanyModel =
        ProductionCompanyModel(
            data.id,
            data.logoPath,
            data.name,
            data.originCountry
        )

    fun mapProductionCountryResponseToProducionCountryModel(data: ProductionCountryResponse): ProductionCountryModel =
        ProductionCountryModel(
            data.iso_3166_1,
            data.name
        )

    fun mapSpokenLanguageResponseToSpokenLanguageModel(data: SpokenLanguagesResponse): SpokenLanguagesModel =
        SpokenLanguagesModel(
            data.iso_639_1,
            data.name
        )

    fun mapCreatedByResponseToCreatedByModel(data: CreatedByResponse): CreatedByModel =
        CreatedByModel(
            data.id,
            data.creditId,
            data.name,
            data.gender,
            data.profilePath
        )

    fun mapGenreResponseToGenreModel(data: GenreResponse): GenreModel = GenreModel(
        data.id,
        data.name
    )

    fun mapEpisodeToAirResponseToEpisodeToAirModel(data: EpisodeToAirResponse?): EpisodeToAirModel =
        EpisodeToAirModel(
            data?.airDate,
            data?.episodeNumber,
            data?.id,
            data?.name,
            data?.overview,
            data?.productionCode,
            data?.seasonNumber,
            data?.showId,
            data?.stillPath,
            data?.voteAverage,
            data?.voteCount
        )

    fun mapNetworkResponseToNetworkModel(data: NetworkResponse?): NetworkModel = NetworkModel(
        data?.name,
        data?.id,
        data?.logoPath,
        data?.originCountry
    )

    fun mapSeasonResponseToSeasonModel(data: SeasonResponse?): SeasonModel = SeasonModel(
        data?.airDate,
        data?.episodeCount,
        data?.id,
        data?.name,
        data?.overview,
        data?.posterPath,
        data?.seasonNumber
    )

    fun mapTvshowResponseToTvshowModel(data: TvShowResponse): TvShowModel = TvShowModel(
        data.backdropPath,
        data.createdBy?.map { mapCreatedByResponseToCreatedByModel(it) },
        data.episodeRunTime,
        data.firstAirDate,
        data.genres?.map { mapGenreResponseToGenreModel(it) },
        data.homepage,
        data.id,
        data.inProduction,
        data.languages,
        data.lastAirDate,
        mapEpisodeToAirResponseToEpisodeToAirModel(data.lastEpisodeToAir),
        data.name,
        mapEpisodeToAirResponseToEpisodeToAirModel(data.nextEpisodeToAir),
        data.networks?.map { mapNetworkResponseToNetworkModel(it) },
        data.numberOfEpisodes,
        data.numberOfSeasons,
        data.originCountry,
        data.originalLanguage,
        data.originalName,
        data.overview,
        data.popularity,
        data.posterPath,
        data.productionCompanies?.map { mapProductionCompanyResponseToProductionCompanyModel(it) },
        data.seasons?.map { mapSeasonResponseToSeasonModel(it) },
        data.status,
        data.tagline,
        data.type,
        data.voteAverage,
        data.voteCount,
        false
    )

    fun mapTvshowWithGenreLanguageEntityToTvshowModel(data: TvshowWithGenreLanguage): TvShowModel =
        TvShowModel(
            data.tvshow.backdropPath,
            null,
            emptyList(),
            data.tvshow.firstAirDate,
            data.genres.map { mapGenreEntityToGenreModel(it) },
            data.tvshow.homepage,
            data.tvshow.id,
            data.tvshow.inProduction,
            data.languages.map { it.name },
            data.tvshow.lastAirDate,
            null,
            data.tvshow.name,
            null,
            emptyList(),
            data.tvshow.numberOfEpisodes,
            data.tvshow.numberOfSeasons,
            emptyList(),
            data.tvshow.originalLanguage,
            data.tvshow.originalName,
            data.tvshow.overview,
            data.tvshow.popularity,
            data.tvshow.posterPath,
            emptyList(),
            emptyList(),
            data.tvshow.status,
            data.tvshow.tagline,
            data.tvshow.type,
            data.tvshow.voteAverage,
            data.tvshow.voteCount,
            false
        )

    fun mapMovieModelToMovieEntity(data: MovieModel): MovieEntity = MovieEntity(
        data.id,
        data.adult,
        data.backdropPath,
        data.budget,
        data.homepage ?: "",
        data.imdbId,
        data.originalLanguage,
        data.originalTitle,
        data.overview,
        data.popularity,
        data.posterPath,
        data.releaseDate,
        data.revenue,
        data.runtime,
        data.status,
        data.tagline,
        data.title,
        data.video,
        data.voteAverage,
        data.voteCount
    )

    fun mapMovieEntityWithGenreLanguageToMovieModel(data: MovieWithGenreLanguage): MovieModel =
        MovieModel(
            data.movie.adult,
            data.movie.backdropPath,
            null,
            data.movie.budget,
            data.genres.map { mapGenreEntityToGenreModel(it) },
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
            data.spokenLanguage.map { mapSpokenLanguageEntityToSpokenLanguageModel(it) },
            data.movie.status,
            data.movie.tagline,
            data.movie.title,
            data.movie.video,
            data.movie.voteAverage,
            data.movie.voteCount,
            data.movie.isFavorite
        )

    fun mapMovieEntityToMovieModel(data: MovieEntity): MovieModel = MovieModel(
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

    fun mapSpokenLanguageModelToSpokenLanguageEntity(
        data: SpokenLanguagesModel,
        movieId: Int
    ): SpokenLanguageEntity = SpokenLanguageEntity(
        movieId = movieId,
        iso_639_1 = data.iso_639_1.toString(),
        name = data.name.toString()
    )

    fun mapSpokenLanguageEntityToSpokenLanguageModel(data: SpokenLanguageEntity): SpokenLanguagesModel =
        SpokenLanguagesModel(
            data.iso_639_1,
            data.name
        )

    fun mapGenreEntityToGenreModel(data: GenreEntity): GenreModel = GenreModel(
        data.tvshowId, data.name
    )

    fun mapGenreModelToGenreEntity(data: GenreModel, movieId: Int?, tvshowId: Int?): GenreEntity =
        GenreEntity(
            genreId = data.id,
            name = data.name ?: "",
            movieId = movieId ?: 0,
            tvshowId = tvshowId ?: 0,
        )

    fun mapLanguageToLanguageEntity(data: String, tvshowId: Int): LanguageEntity = LanguageEntity(
        0,
        tvshowId,
        data
    )

    fun mapTvshowModelToTvshowEntity(data: TvShowModel): TvshowEntity = TvshowEntity(
        data.id,
        data.backdropPath,
        data.firstAirDate,
        data.homepage,
        data.inProduction,
        data.lastAirDate,
        data.name ?: "",
        data.numberOfEpisodes,
        data.numberOfSeasons,
        data.originalLanguage,
        data.originalName,
        data.overview,
        data.popularity,
        data.posterPath,
        data.status,
        data.tagline,
        data.type,
        data.voteAverage,
        data.voteCount
    )

    fun mapTvshowEntityWithGenreLanguageToTvShowModel(data: TvshowWithGenreLanguage): TvShowModel =
        TvShowModel(
            data.tvshow.backdropPath,
            emptyList(),
            emptyList(),
            data.tvshow.firstAirDate,
            data.genres.map { mapGenreEntityToGenreModel(it) },
            data.tvshow.homepage,
            data.tvshow.id,
            data.tvshow.inProduction,
            data.languages.map { it.name },
            data.tvshow.lastAirDate,
            null,
            data.tvshow.name,
            null,
            emptyList(),
            data.tvshow.numberOfEpisodes,
            data.tvshow.numberOfSeasons,
            emptyList(),
            data.tvshow.originalLanguage,
            data.tvshow.originalName,
            data.tvshow.overview,
            data.tvshow.popularity,
            data.tvshow.posterPath,
            emptyList(),
            emptyList(),
            data.tvshow.status,
            data.tvshow.tagline,
            data.tvshow.type,
            data.tvshow.voteAverage,
            data.tvshow.voteCount,
            data.tvshow.isFavorite
        )

    fun mapTvshowEntityToTvShowModel(data: TvshowEntity): TvShowModel = TvShowModel(
        data.backdropPath,
        emptyList(),
        emptyList(),
        data.firstAirDate,
        emptyList(),
        data.homepage,
        data.id,
        data.inProduction,
        emptyList(),
        data.lastAirDate,
        null,
        data.name,
        null,
        emptyList(),
        data.numberOfEpisodes,
        data.numberOfSeasons,
        emptyList(),
        data.originalLanguage,
        data.originalName,
        data.overview,
        data.popularity,
        data.posterPath,
        emptyList(),
        emptyList(),
        data.status,
        data.tagline,
        data.type,
        data.voteAverage,
        data.voteCount,
        data.isFavorite
    )

}