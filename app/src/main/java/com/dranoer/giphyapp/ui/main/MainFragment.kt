package com.dranoer.giphyapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
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

        viewModel.getTrends()
        binding.progressbar.isVisible = true

        val recyclerView = binding.recyclerview
        val adapter = MainAdapter(MainAdapter.OnClickListener { itemId ->
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allGiphies.observe(viewLifecycleOwner) {
            it.let { adapter.submitList(it) }
            binding.progressbar.isGone = true
        }

        binding.searchView.onQueryTextChanged {
            viewModel.search(it)
        }

        return view
    }
}