package com.ohmymovies.core.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module

object DepsModuleProvider {
    @ExperimentalCoroutinesApi
    val modules : List<Module> = ArrayList<Module>().apply {
        addAll(NetworkModules.modules)
        addAll(RepositoryModules.modules)
        addAll(UseCaseModules.modules)
    }
}