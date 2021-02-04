package com.dicoding.ohmymovies.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.databinding.FragmentTvshowsBinding
import com.dicoding.ohmymovies.detailtvshow.DetailTvshowActivity
import com.dicoding.ohmymovies.util.EventObserver
import com.ohmymovies.core.ui.args.DetailTvshowActivityArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvshowsFragment : Fragment() {

    private lateinit var binding: FragmentTvshowsBinding

    private val tvshowsViewModel: TvshowsViewModel by viewModel()

    private var tvshowIdTempOpen: Int = 0

    private val listAdapter = TvshowsAdapter {
        openDetailTvshow(it.id, it.name, it.isFavorite)
    }

    init {
        TvshowsModule.loadModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvshowsBinding.inflate(inflater, container, false).apply {
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
        with(tvshowsViewModel){
            tvshows.observe(viewLifecycleOwner){
                listAdapter.addList(it)
            }

            refreshTvshowsEvent.observe(viewLifecycleOwner, EventObserver {
                binding.swipeRefreshLayout.isRefreshing = false
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
            tvshowsViewModel.fetchTvshows(isFromSwipe = true)
        }
        with(binding.listTvshows) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        tvshowsViewModel.checkFavoriteState(tvshowIdTempOpen)
    }

    private fun openDetailTvshow(id: Int?, title: String?, isFavorite: Boolean?) {
        tvshowIdTempOpen = id ?: 0
        val args = DetailTvshowActivityArgs(id, title, false, isFavorite)
        val intent = Intent(activity, DetailTvshowActivity::class.java)
        intent.putExtra(DetailTvshowActivity.ARGS, args)
        startActivity(intent)
    }
}