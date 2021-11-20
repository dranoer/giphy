package com.dranoer.giphyapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dranoer.giphyapp.data.remote.Resource
import com.dranoer.giphyapp.databinding.FragmentMainBinding
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrends().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("nazanin", "loading state")
                }
                is Resource.Success -> {
                    Log.d("nazanin", "success state")
                }
                is Resource.Failure -> {
                    Log.d("nazanin", "fail state")
                }
            }

        }
    }
}