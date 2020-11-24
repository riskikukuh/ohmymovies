package com.dicoding.ohmymovies.data.di

import com.dicoding.ohmymovies.data.source.DefaultMovieRepository
import com.dicoding.ohmymovies.data.source.MovieDataSource
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.data.source.remote.MovieRemoteDataSource
import com.dicoding.ohmymovies.util.Constants
import org.koin.core.module.Module
import org.koin.dsl.module

object RepositoryModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(repoModule, remoteDataSource)

    private val repoModule = module {
        single<MovieRepository> { DefaultMovieRepository(dataSource = get())}
    }

    private val remoteDataSource = module{
        single<MovieDataSource> { MovieRemoteDataSource(get(), apiKey = Constants.API_KEY) }
    }
}