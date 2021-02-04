package com.dicoding.ohmymovies.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(private val listPage: List<Fragment> = listOf(), fragmentManager:FragmentManager, lifeCycle : Lifecycle) : FragmentStateAdapter(fragmentManager, lifeCycle){

    override fun getItemCount(): Int = listPage.size

    override fun createFragment(position: Int): Fragment {
        return listPage[position]
    }

}