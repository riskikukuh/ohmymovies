package com.dicoding.ohmymovies.ui.tvshows

import com.dicoding.ohmymovies.data.di.BaseViewModelProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object TvshowsModule : BaseViewModelProvider{
    override fun loadModules() = lazyLoadModule

    private val lazyLoadModule by lazy {
        loadKoinModules(viewModelModule)
    }

    private val viewModelModule = module {
        viewModel { TvshowsViewModel(androidApplication(), get()) }
    }

}