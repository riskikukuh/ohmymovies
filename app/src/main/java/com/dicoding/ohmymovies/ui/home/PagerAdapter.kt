package com.dicoding.ohmymovies.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.ohmymovies.ui.movies.MoviesFragment
import com.dicoding.ohmymovies.ui.tvshows.TvshowsFragment

class PagerAdapter(private val listPage: List<Fragment> = listOf(), fragmentManager:FragmentManager, lifeCycle : Lifecycle) : FragmentStateAdapter(fragmentManager, lifeCycle){

    override fun getItemCount(): Int = listPage.size

    override fun createFragment(position: Int): Fragment {
        return listPage[position]
    }

}