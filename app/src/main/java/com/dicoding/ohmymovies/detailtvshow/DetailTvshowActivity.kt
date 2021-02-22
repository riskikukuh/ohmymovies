package com.dicoding.ohmymovies.detailtvshow

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.adapter.GenresAdapter
import com.dicoding.ohmymovies.databinding.ActivityDetailTvshowBinding
import com.dicoding.ohmymovies.util.setupToolbar
import com.ohmymovies.core.ui.args.DetailTvshowActivityArgs
import com.ohmymovies.core.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTvshowActivity : AppCompatActivity() {

    companion object {
        const val ARGS = "DetailTvshowActivityArgs"
    }

    private var args: DetailTvshowActivityArgs? = null
    private val detailTvshowViewModel: DetailTvshowViewModel by viewModel()
    private val genresAdapter by lazy { GenresAdapter {} }
    private lateinit var binding: ActivityDetailTvshowBinding
    private var itemFavorite: MenuItem? = null
    private var itemNotFavorite: MenuItem? = null

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
                genresAdapter.addList(it.genres ?: emptyList())
            }

            errorMessage.observe(this@DetailTvshowActivity) {
                binding.error.message = it
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.favorite_menu_detail_activity, menu)
        itemFavorite = menu?.findItem(R.id.removeFromFavorite)
        itemNotFavorite = menu?.findItem(R.id.addToFavorite)
        if (args != null) {
            if (args!!.isFavorite != null && args!!.isFavorite!!) {
                itemNotFavorite?.isVisible = false
            } else {
                itemFavorite?.isVisible = false
            }
        } else {
            itemFavorite?.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addToFavorite -> {
                detailTvshowViewModel.addToFavorite {
                    itemFavorite?.isVisible = true
                    item.isVisible = false
                }
                true
            }
            R.id.removeFromFavorite -> {
                detailTvshowViewModel.removeFromFavorite {
                    itemNotFavorite?.isVisible = true
                    item.isVisible = false
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}