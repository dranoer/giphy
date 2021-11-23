package com.dranoer.giphyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dranoer.giphyapp.databinding.FragmentMainBinding
import com.dranoer.giphyapp.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview()

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            it?.let {
                renderUI(it)
            }
        }

        binding.searchView.onQueryTextChanged(onQueryTextChanged = {
            viewModel.search(it)
        }, onTextCleared = {
            viewModel.getTrends()
        })

    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerview
        val adapter = MainAdapter { giphy ->
            viewModel.updateFavorite(giphy)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun renderUI(viewState: MainViewModel.MainViewState) {
        binding.progressbar.isVisible = (viewState.layoutState == ContentState.Loading)
        (binding.recyclerview.adapter as MainAdapter).submitList(viewState.giphyList)
    }
}