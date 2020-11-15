package com.dicoding.ohmymovies.ui.detailMovie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.databinding.ActivityDetailMovieBinding
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.util.getViewModelFactory
import com.dicoding.ohmymovies.util.setupToolbar

class DetailMovieActivity : AppCompatActivity() {

    companion object{
        val TAG = DetailMovieActivity::class.java.simpleName
        const val ARGS = "DetailMovieActivityArgs"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private val genresAdapter = GenresAdapter()
    private var args: DetailMovieActivityArgs? = null
    private val detailMovieViewModel by viewModels<DetailMovieViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@DetailMovieActivity
            viewModel = detailMovieViewModel
            title = args?.title
        }
        setContentView(binding.root)
        args = intent.getParcelableExtra(ARGS)
        setupView()
        setupObserver()

    }

    private fun setupObserver() {
        with(detailMovieViewModel) {
            movie.observe(this@DetailMovieActivity) {
                if (it.posterImageResource > 0) binding.posterMovie.setImageResource(it.posterImageResource)
                if (it.genres != null) genresAdapter.addGenres(it.genres)
            }

            errorException.observe(this@DetailMovieActivity) {
                binding.error.exception = it
            }
        }
    }

    private fun setupView() {
        detailMovieViewModel.onNavArgs(args)
        setupToolbar(binding.toolbar, args?.title){
            finish()
        }
        with(binding.genres) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }
    }
}

