package com.dicoding.ohmymovies.ui.detailTvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.databinding.ActivityDetailTvshowBinding
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.util.getViewModelFactory
import com.dicoding.ohmymovies.util.setupToolbar


class DetailTvshowActivity : AppCompatActivity() {

    companion object{
        val TAG = DetailTvshowActivity::class.java.simpleName
        const val ARGS = "DetailTvshowActivityArgs"
    }

    private var args : DetailTvshowActivityArgs? = null
    private val detailTvshowViewModel by viewModels<DetailTvshowViewModel> { getViewModelFactory() }
    private lateinit var binding : ActivityDetailTvshowBinding
    private val genresAdapter by lazy { GenresAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = intent.getParcelableExtra(ARGS)
        binding = ActivityDetailTvshowBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@DetailTvshowActivity
            viewModel = detailTvshowViewModel
            title = args?.title
        }
        setContentView(binding.root)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        with(detailTvshowViewModel) {
            tvshow.observe(this@DetailTvshowActivity) {
                binding.posterTvshow.setImageResource(it.posterImageResource ?: 0)
                if (it.genres != null && it.genres.isNotEmpty()) genresAdapter.addGenres(it.genres)
            }

            errorException.observe(this@DetailTvshowActivity) {
                binding.error.exception = it
            }
        }
    }

    private fun setupView() {
        detailTvshowViewModel.onNavArgs(args)
        setupToolbar(binding.toolbar, args?.title) {
            finish()
        }
        with(binding.genres) {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}