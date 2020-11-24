package com.dicoding.ohmymovies.ui.detailTvshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.databinding.ActivityDetailTvshowBinding
import com.dicoding.ohmymovies.ui.adapter.GenresAdapter
import com.dicoding.ohmymovies.util.Constants
import com.dicoding.ohmymovies.util.setupToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailTvshowActivity : AppCompatActivity() {

    companion object {
        val TAG = DetailTvshowActivity::class.java.simpleName
        const val ARGS = "DetailTvshowActivityArgs"
    }

    private var args: DetailTvshowActivityArgs? = null
    private val detailTvshowViewModel: DetailTvshowViewModel by viewModel()
    private val genresAdapter by lazy { GenresAdapter() }
    private lateinit var binding: ActivityDetailTvshowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailTvshowModule.loadModules()
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
            tvshowResponse.observe(this@DetailTvshowActivity) {
                Glide.with(this@DetailTvshowActivity)
                    .load(Constants.BASE_URL_POSTER + it.posterPath).into(binding.posterTvshow)
                genresAdapter.addGenres(it.genres ?: emptyList())
            }

            errorException.observe(this@DetailTvshowActivity) {
                binding.error.exception = it
            }
        }
    }

    private fun setupView() {
        detailTvshowViewModel.onNavArgs(this, args)
        setupToolbar(binding.toolbar, args?.title) {
            finish()
        }
        with(binding.genres) {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}