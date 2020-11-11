package com.dicoding.ohmymovies.data.di

import com.dicoding.ohmymovies.data.source.DefaultMovieRepository
import com.dicoding.ohmymovies.data.source.MovieDataSource
import com.dicoding.ohmymovies.data.source.MovieRepository
import com.dicoding.ohmymovies.data.source.local.MoviesLocalDataSource

object ServiceLocator{
    private var movieRepository: MovieRepository? = null

    fun provideMovieRepository() : MovieRepository{
        if (movieRepository == null) {
            movieRepository = DefaultMovieRepository(createMovieLocalDataSource())
        }
        return movieRepository!!
    }

    private fun createMovieLocalDataSource(): MovieDataSource {
        return MoviesLocalDataSource()
    }


}