package com.dicoding.ohmymovies.ui.detailMovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.data.model.DetailMovieActivityArgs
import com.dicoding.ohmymovies.databinding.ActivityDetailMovieBinding
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.util.Constants
import com.dicoding.ohmymovies.util.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        val TAG = DetailMovieActivity::class.java.simpleName
        const val ARGS = "DetailMovieActivityArgs"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private val genresAdapter = GenresAdapter()
    private var args: DetailMovieActivityArgs? = null
    private val detailMovieViewModel: DetailMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailMovieModule.loadModules()
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
            movieResponse.observe(this@DetailMovieActivity) {
                Glide.with(this@DetailMovieActivity).load(Constants.BASE_URL_POSTER + it.posterPath)
                    .into(binding.posterMovie)
                genresAdapter.addGenres(it.genres ?: emptyList())
            }

            errorException.observe(this@DetailMovieActivity) {
                binding.error.exception = it
            }
        }
    }

    private fun setupView() {
        detailMovieViewModel.onNavArgs(this, args)
        setupToolbar(binding.toolbar, args?.title) {
            finish()
        }
        with(binding.genres) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }
    }
}

