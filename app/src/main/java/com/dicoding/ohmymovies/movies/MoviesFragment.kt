package com.dicoding.ohmymovies.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.databinding.FragmentMoviesBinding
import com.dicoding.ohmymovies.detailmovie.DetailMovieActivity
import com.dicoding.ohmymovies.util.EventObserver
import com.ohmymovies.core.ui.args.DetailMovieActivityArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val TAG = MoviesFragment::class.java.simpleName

    private lateinit var binding: FragmentMoviesBinding

    private val moviesViewModel: MoviesViewModel by viewModel()

    private var movieIdTempOpen: Int = 0

    private val listAdapter = MoviesAdapter {
        openDetailMovie(it.id, it.title, it.isFavorite)
    }

    init {
        MoviesModule.loadModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                listAdapter.addList(it)
            }

            refreshMoviesEvent.observe(viewLifecycleOwner, EventObserver {
                binding.swipeRefreshLayout.isRefreshing = it
            })

            emptyMessage.observe(viewLifecycleOwner) {
                binding.empty.msg = it
            }

            errorMessage.observe(viewLifecycleOwner) {
                binding.error.message = it
            }
        }
    }

    private fun setupComponent() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.fetchMovies(isFromSwipe = true)
        }
        with(binding.listMovie) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        moviesViewModel.checkFavoriteState(movieIdTempOpen)
    }

    private fun openDetailMovie(id: Int?, title: String?, isFavorite: Boolean?) {
        movieIdTempOpen = id ?: 0
        val args = DetailMovieActivityArgs(id, title, false, isFavorite)
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.ARGS, args)
        requireContext().startActivity(intent)
    }

}