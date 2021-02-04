package com.dicoding.ohmymovies

import android.app.Application
import com.ohmymovies.core.di.DepsModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(DepsModuleProvider.modules)
        }
    }
}