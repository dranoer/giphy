package com.dranoer.giphyapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.databinding.FragmentMainBinding
import com.dranoer.giphyapp.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.getTrends()

        val recyclerView = binding.recyclerview
        val adapter = MainAdapter(MainAdapter.OnClickListener { itemId ->
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getTrends().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressbar.isVisible = true
                }
                is Resource.Success -> {
                    result.data.let {
                        adapter.submitList(it)
                    }
                    binding.progressbar.isGone = true
                }
                is Resource.Failure -> {
                    binding.progressbar.isGone = true
                    Log.d("nazanin", "fail state")
                }
            }

        }

        binding.searchView.onQueryTextChanged {
            viewModel.search(it)
        }

        return view
    }
}