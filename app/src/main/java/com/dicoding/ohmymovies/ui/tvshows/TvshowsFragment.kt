package com.dicoding.ohmymovies.ui.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.ohmymovies.data.EventObserver
import com.dicoding.ohmymovies.data.model.DetailTvshowActivityArgs
import com.dicoding.ohmymovies.databinding.FragmentTvshowsBinding
import com.dicoding.ohmymovies.ui.detailTvshow.DetailTvshowActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvshowsFragment : Fragment() {

    private lateinit var binding : FragmentTvshowsBinding

    private val tvshowsViewModel : TvshowsViewModel by viewModel()

    private val listAdapter = TvshowsAdapter{
        openDetailTvshow(it.id, it.name)
    }

    init {
        TvshowsModule.loadModules()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                listAdapter.addTvshows(it)
            }

            refreshTvshowsEvent.observe(viewLifecycleOwner, EventObserver{
                binding.swipeRefreshLayout.isRefreshing = false
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
        binding.swipeRefreshLayout.setOnRefreshListener {
            tvshowsViewModel.fetchTvshows(isFromSwipe = true)
        }
        with(binding.listTvshows){
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun openDetailTvshow(id : Int?, title : String?){
        val args = DetailTvshowActivityArgs(id, title)
        val intent = Intent(activity, DetailTvshowActivity::class.java)
        intent.putExtra(DetailTvshowActivity.ARGS, args)
        startActivity(intent)
    }
}