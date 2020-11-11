package com.dicoding.ohmymovies

import android.app.Application
import com.dicoding.ohmymovies.data.di.ServiceLocator

class MyApp : Application(){

    val movieRepository = ServiceLocator.provideMovieRepository()

}