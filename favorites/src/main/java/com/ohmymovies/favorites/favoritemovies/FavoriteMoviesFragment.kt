package com.ohmymovies.favorites.favoritemovies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.detailmovie.DetailMovieActivity
import com.ohmymovies.core.data.Result.Error
import com.ohmymovies.core.data.Result.Success
import com.ohmymovies.core.ui.args.DetailMovieActivityArgs
import com.ohmymovies.favorites.databinding.FragmentFavoriteMoviesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMoviesFragment : Fragment() {
    private val TAG = FavoriteMoviesFragment::class.java.simpleName

    private lateinit var binding: FragmentFavoriteMoviesBinding

    private val moviesViewModel: FavoriteMoviesViewModel by viewModel()

    private val listAdapter = FavoriteMoviesAdapter {
        openDetailMovie(it.id, it.title, it.isFavorite)
    }

    init {
        FavoriteMoviesModule.loadModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false).apply {
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
        with(moviesViewModel) {

            fetchFavoriteMovies().observe(viewLifecycleOwner) {
                when (it) {
                    is Success -> {
                        with(binding) {
                            loading.visibility = View.GONE
                            error.root.visibility = View.GONE
                            if (it.data.isEmpty()) {
                                listFavoriteMovie.visibility = View.INVISIBLE
                                empty.root.visibility = View.VISIBLE
                                empty.msg = getString(R.string.movies_empty)
                            } else {
                                listFavoriteMovie.visibility = View.VISIBLE
                                empty.root.visibility = View.GONE
                                listAdapter.submitList(it.data)
                                listAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                    is Error -> {
                        with(binding) {
                            loading.visibility = View.GONE
                            error.root.visibility = View.VISIBLE
                            listFavoriteMovie.visibility = View.INVISIBLE
                            empty.root.visibility = View.GONE
                            error.message = it.message
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupComponent() {
        with(binding.listFavoriteMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun openDetailMovie(id: Int?, title: String?, isFavorite: Boolean?) {
        val args = DetailMovieActivityArgs(id, title, true, isFavorite)
        val intent = Intent(requireContext(), DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.ARGS, args)
        requireContext().startActivity(intent)
    }
}