package com.dicoding.ohmymovies.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.databinding.ActivityHomeBinding
import com.dicoding.ohmymovies.ui.favorite.FavoriteActivity
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

    private fun openFavoritePage() {
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.apply {
            inflate(R.menu.favorite_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                openFavoritePage()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}