package com.dicoding.ohmymovies.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.databinding.ActivityHomeBinding
import com.dicoding.ohmymovies.ui.movies.MoviesFragment
import com.dicoding.ohmymovies.ui.tvshows.TvshowsFragment
import com.dicoding.ohmymovies.util.setupToolbar
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private val tabLayoutStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.tabName)[position]
        }

    private val listPage = listOf(
        MoviesFragment(),
        TvshowsFragment()
    )

    private lateinit var adapter : PagerAdapter
    private lateinit var tabMediator : TabLayoutMediator
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupComponent()
    }

    private fun setupComponent() {
        setupToolbar(binding.toolbar, getString(R.string.app_name), false)
        adapter = PagerAdapter(listPage, supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager, tabLayoutStrategy)
        tabMediator.attach()
    }
}