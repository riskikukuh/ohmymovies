package com.dicoding.ohmymovies.util

import androidx.fragment.app.Fragment
import com.dicoding.ohmymovies.MyApp
import com.dicoding.ohmymovies.data.ViewModelFactory

fun Fragment.getViewModelFactoryTest() : ViewModelFactory{
    val repo = (requireContext().applicationContext as MyApp).movieRepository
    return ViewModelFactory(requireActivity().application, repo)
}