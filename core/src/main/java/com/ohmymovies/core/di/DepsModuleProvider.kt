package com.ohmymovies.core.di

import org.koin.core.module.Module

object DepsModuleProvider {
    val modules : List<Module> = ArrayList<Module>().apply {
        addAll(NetworkModules.modules)
        addAll(RepositoryModules.modules)
        addAll(UseCaseModules.modules)
    }
}