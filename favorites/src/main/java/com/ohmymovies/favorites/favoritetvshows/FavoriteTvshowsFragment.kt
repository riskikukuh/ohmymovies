package com.ohmymovies.favorites.favoritetvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.R
import com.dicoding.ohmymovies.detailtvshow.DetailTvshowActivity
import com.ohmymovies.core.data.Result.Error
import com.ohmymovies.core.data.Result.Success
import com.ohmymovies.core.ui.args.DetailTvshowActivityArgs
import com.ohmymovies.favorites.databinding.FragmentFavoriteTvshowsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvshowsFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteTvshowsBinding

    private val tvshowsViewModel: FavoriteTvshowsViewModel by viewModel()

    private val listAdapter = FavoriteTvshowsAdapter {
        openDetailMovie(it.id, it.name, it.isFavorite)
    }

    init {
        FavoriteTvshowsModule.loadModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTvshowsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = tvshowsViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponent()
        setupObserver()
    }

    private fun setupObserver() {
        with(tvshowsViewModel) {
            fetchFavoriteTvshows().observe(viewLifecycleOwner) {
                when (it) {
                    is Success -> {
                        with(binding) {
                            error.root.visibility = View.GONE
                            loading.visibility = View.GONE
                            if (it.data.isEmpty()) {
                                listFavoriteTvshow.visibility = View.INVISIBLE
                                empty.root.visibility = View.VISIBLE
                                empty.msg = getString(R.string.tvshows_empty)
                            } else {
                                listFavoriteTvshow.visibility = View.VISIBLE
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
                            error.message = it.message
                            empty.root.visibility = View.GONE
                            listFavoriteTvshow.visibility = View.INVISIBLE
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupComponent() {
        with(binding.listFavoriteTvshow) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun openDetailMovie(id: Int?, title: String?, isFavorite: Boolean?) {
        val args = DetailTvshowActivityArgs(id, title, true, isFavorite)
        val intent = Intent(requireContext(), DetailTvshowActivity::class.java)
        intent.putExtra(DetailTvshowActivity.ARGS, args)
        requireContext().startActivity(intent)
    }
}