package com.dicoding.ohmymovies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.data.EventObserver
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.data.model.MovieModel
import com.dicoding.ohmymovies.databinding.FragmentMoviesBinding
import com.dicoding.ohmymovies.ui.detailMovie.DetailMovieActivity
import com.dicoding.ohmymovies.util.getViewModelFactory

class MoviesFragment : Fragment() {

    private val TAG = MoviesFragment::class.java.simpleName

    private lateinit var binding: FragmentMoviesBinding

    private val moviesViewModel by viewModels<MoviesViewModel> { getViewModelFactory() }

    private val listAdapter = MoviesAdapter {
        openDetailMovie(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = moviesViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponent()
        setupObserver()
    }

    private fun setupObserver() {
        with(moviesViewModel){
            movies.observe(viewLifecycleOwner) {
                listAdapter.addMovies(it)
            }

            refreshMoviesEvent.observe(viewLifecycleOwner, EventObserver{
                binding.swipeRefreshLayout.isRefreshing = it
            })

            emptyMessage.observe(viewLifecycleOwner){
                binding.empty.msg = it
            }

            errorException.observe(viewLifecycleOwner){
                binding.error.exception = it
            }
        }
    }

    private fun setupComponent() {
        binding.swipeRefreshLayout.setOnRefreshListener{
            moviesViewModel.fetchMovies(true, isFromSwipe = true)
        }
        with(binding.listMovie) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun openDetailMovie(data : MovieModel?){
        val args = DetailMovieActivityArgs(data?.title ?: "", data)
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.ARGS, args)
        Log.d(TAG, "openDetailMovie $args")
        requireContext().startActivity(intent)
    }

}