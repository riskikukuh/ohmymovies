package com.dicoding.ohmymovies.data.di

import androidx.room.Room
import com.dicoding.ohmymovies.data.source.DefaultMovieRepository
import com.dicoding.ohmymovies.data.source.MovieDataSource
import com.dicoding.ohmymovies.data.source.MovieLocalDataSource
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.data.source.local.FavoritesDao
import com.dicoding.ohmymovies.data.source.local.FavoritesDatabase
import com.dicoding.ohmymovies.data.source.local.MoviesLocalDataSource
import com.dicoding.ohmymovies.data.source.remote.MovieRemoteDataSource
import com.dicoding.ohmymovies.util.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object RepositoryModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(db, localDataSource, repoModule, remoteDataSource)

    private val repoModule = module {
        single<MovieRepository> {
            DefaultMovieRepository(
                context = androidContext(),
                localDataSource = get(),
                remoteDataSource = get()
            )
        }
    }

    private val remoteDataSource = module{
        single<MovieDataSource> { MovieRemoteDataSource(get(), apiKey = Constants.API_KEY) }
    }

    private val localDataSource = module {
        single<MovieLocalDataSource> { MoviesLocalDataSource(get()) }
    }

    private val db = module {
        single {
            Room.databaseBuilder(
                androidContext().applicationContext,
                FavoritesDatabase::class.java,
                "favoritesDatabase"
            ).fallbackToDestructiveMigration().build()
        }
        factory<FavoritesDao> { get<FavoritesDatabase>().favoritesDao() }
    }

}