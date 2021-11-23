package com.dranoer.giphyapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dranoer.giphyapp.databinding.FragmentMainBinding
import com.dranoer.giphyapp.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerview()

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            it?.let {
                renderUI(it)
            }
        }

        binding.searchView.onQueryTextChanged {
            viewModel.search(it)
        }

        return view
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerview
        val adapter = MainAdapter(
            MainAdapter.OnClickListener { itemId ->
                viewModel.updateFavorite(itemId)
                Log.d("nazanin", "id is $itemId")
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun renderUI(viewState: MainViewModel.MainViewState) {
        binding.progressbar.isVisible = (viewState.layoutState == ContentState.Loading)
        (binding.recyclerview.adapter as MainAdapter).submitList(viewState.giphyList)
    }
}