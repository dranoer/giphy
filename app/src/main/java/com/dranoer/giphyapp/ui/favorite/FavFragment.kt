package com.dranoer.giphyapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dranoer.giphyapp.databinding.FragmentFavBinding
import com.dranoer.giphyapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview()
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            it?.let {
                renderUI(it)
            }
        }
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.favoriteRecyclerview
        val adapter = FavAdapter { giphy ->
            viewModel.updateFavorite(giphy)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun renderUI(viewState: MainViewModel.MainViewState) {
        (binding.favoriteRecyclerview.adapter as FavAdapter).submitList(viewState.favGiphyList)
    }
}