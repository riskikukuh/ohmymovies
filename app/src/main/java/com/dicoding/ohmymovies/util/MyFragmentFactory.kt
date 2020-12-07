package com.dicoding.ohmymovies.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.dicoding.ohmymovies.ui.favoriteMovies.FavoriteMoviesFragment
import com.dicoding.ohmymovies.ui.favoriteTvshows.FavoriteTvshowsFragment
import com.dicoding.ohmymovies.ui.movies.MoviesFragment
import com.dicoding.ohmymovies.ui.tvshows.TvshowsFragment

class MyFragmentFactory : FragmentFactory() {

    private val TAG = MyFragmentFactory::class.java.simpleName

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            MoviesFragment::class.java.name -> {
                MoviesFragment()
            }
            TvshowsFragment::class.java.name -> {
                TvshowsFragment()
            }
            FavoriteTvshowsFragment::class.java.name -> {
                FavoriteTvshowsFragment()
            }
            FavoriteMoviesFragment::class.java.name -> {
                FavoriteMoviesFragment()
            }
            else -> super.instantiate(classLoader, className)
        }

}