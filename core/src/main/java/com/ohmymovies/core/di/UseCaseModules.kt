package com.ohmymovies.core.di

import com.ohmymovies.core.domain.usecase.MovieInteractor
import com.ohmymovies.core.domain.usecase.MovieUseCase
import com.ohmymovies.core.domain.usecase.TvshowInteractor
import com.ohmymovies.core.domain.usecase.TvshowUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCaseModules : BaseModule {
    override val modules: List<Module>
        get() = listOf(useCaseModule)

    private val useCaseModule = module {
        single<MovieUseCase> { MovieInteractor(get()) }
        single<TvshowUseCase> { TvshowInteractor(get()) }
    }
}