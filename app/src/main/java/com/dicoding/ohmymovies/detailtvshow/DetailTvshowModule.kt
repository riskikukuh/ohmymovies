package com.dicoding.ohmymovies.detailtvshow

import com.ohmymovies.core.di.BaseViewModelProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DetailTvshowModule : BaseViewModelProvider {
    override fun loadModules() = lazyLoadModule

    private val lazyLoadModule by lazy {
        loadKoinModules(viewModelModule)
    }

    private val viewModelModule = module {
        viewModel {
            DetailTvshowViewModel(
                application = androidApplication(),
                tvshowUseCase = get()
            )
        }
    }

}