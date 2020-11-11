package com.dicoding.ohmymovies.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.ohmymovies.MyApp
import com.dicoding.ohmymovies.data.ViewModelFactory

fun Fragment.getViewModelFactory() : ViewModelFactory {
    val movieRepository = (requireContext().applicationContext as MyApp).movieRepository
    return ViewModelFactory(requireActivity().application, movieRepository)
}

fun AppCompatActivity.getViewModelFactory() : ViewModelFactory {
    val movieRepository = (applicationContext as MyApp).movieRepository
    return ViewModelFactory(application, movieRepository)
}