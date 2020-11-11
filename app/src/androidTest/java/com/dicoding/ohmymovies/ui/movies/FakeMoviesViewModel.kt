package com.dicoding.ohmymovies.ui.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.ohmymovies.data.source.MovieRepository
import kotlin.coroutines.CoroutineContext

class FakeMoviesViewModel(
    application: Application,
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineContext
) : MoviesViewModel(application, movieRepository, dispatcher) {



}