package com.dicoding.ohmymovies.ui.favoriteTvshows

import com.ohmymovies.core.di.BaseViewModelProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object FavoriteTvshowsModule : BaseViewModelProvider {
    override fun loadModules() = lazyLoadModule

    private val lazyLoadModule by lazy { loadKoinModules(viewModelModule) }

    private val viewModelModule = module {
        viewModel { FavoriteTvshowsViewModel(get()) }
    }

}