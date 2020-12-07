package com.dicoding.ohmymovies.ui.favoriteMovies

import com.dicoding.ohmymovies.data.di.BaseViewModelProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object FavoriteMoviesModule : BaseViewModelProvider {
    override fun loadModules() = lazyLoadModule

    private val lazyLoadModule by lazy {
        loadKoinModules(viewModelModule)
    }

    private val viewModelModule = module {
        viewModel { FavoriteMoviesViewModel(get()) }
    }

}