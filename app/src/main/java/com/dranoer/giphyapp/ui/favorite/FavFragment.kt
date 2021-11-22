package com.dranoer.giphyapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dranoer.giphyapp.data.model.GiphyEntity
import com.dranoer.giphyapp.databinding.FragmentFavBinding
import com.dranoer.giphyapp.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.favoriteRecyclerview
        val adapter = FavAdapter(
            FavAdapter.OnClickListener {
                viewModel.updateFavorite(it)
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.submitList(
            listOf(
                GiphyEntity(id = "1", isFavorite = true),
                GiphyEntity(id = "2", isFavorite = false),
            )
        )

        return view
    }
}