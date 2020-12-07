package com.dicoding.ohmymovies.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.databinding.ActivityFavoriteBinding
import com.dicoding.ohmymovies.ui.favoriteMovies.FavoriteMoviesFragment
import com.dicoding.ohmymovies.ui.favoriteTvshows.FavoriteTvshowsFragment
import com.dicoding.ohmymovies.util.setupToolbar
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {

    private val tabLayoutStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.tabName)[position]
        }

    private val listPage = listOf(
        FavoriteMoviesFragment(),
        FavoriteTvshowsFragment()
    )

    private lateinit var adapter: PagerAdapterFavorite
    private lateinit var tabMediator: TabLayoutMediator
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupComponent()
    }

    private fun setupComponent() {
        setupToolbar(binding.favoriteToolbar, "", false)
        adapter = PagerAdapterFavorite(listPage, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        tabMediator =
            TabLayoutMediator(binding.favoriteTabLayout, binding.viewPager, tabLayoutStrategy)
        tabMediator.attach()
    }
}