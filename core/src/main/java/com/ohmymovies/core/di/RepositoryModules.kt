package com.ohmymovies.core.di

import androidx.room.Room
import com.ohmymovies.core.data.source.MoviesRepository
import com.ohmymovies.core.data.source.local.MoviesLocalDataSource
import com.ohmymovies.core.data.source.local.room.FavoritesDatabase
import com.ohmymovies.core.data.source.remote.MovieRemoteDataSource
import com.ohmymovies.core.domain.repository.IMoviesRepository
import com.ohmymovies.core.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object RepositoryModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(db, localDataSource, repoModule, remoteDataSource)

    private val repoModule = module {
        single<IMoviesRepository> {
            MoviesRepository(
                androidContext(),
                get(),
                get()
            )
        }
    }

    private val remoteDataSource = module {
        single { MovieRemoteDataSource(androidContext(), get(), apiKey = Constants.API_KEY) }
    }

    private val localDataSource = module {
        single { MoviesLocalDataSource(androidContext(), get()) }
    }

    private val db = module {
        single {
            Room.databaseBuilder(
                androidContext().applicationContext,
                FavoritesDatabase::class.java,
                "favoritesDatabase"
            ).fallbackToDestructiveMigration().build()
        }
        factory { get<FavoritesDatabase>().favoritesDao() }
    }

}