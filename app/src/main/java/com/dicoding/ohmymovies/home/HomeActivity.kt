package com.dicoding.ohmymovies.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.movies.MoviesFragment
import com.dicoding.ohmymovies.tvshows.TvshowsFragment
import com.dicoding.ohmymovies.util.setupToolbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupComponent()
    }

    private fun setupComponent() {
        setupToolbar(toolbar, getString(R.string.app_name), false)
        adapter = PagerAdapter(listPage, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        tabMediator = TabLayoutMediator(tabLayout, viewPager, tabLayoutStrategy)
        tabMediator.attach()
    }

    private fun openFavoritePage() {
        val intent =
            Intent(this, Class.forName("com.ohmymovies.favorites.favorite.FavoriteActivity"))
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