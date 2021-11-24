package com.dranoer.giphyapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dranoer.giphyapp.ui.favorite.FavFragment
import com.dranoer.giphyapp.ui.main.MainFragment

private const val NUM_TABS = 2

public class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MainFragment()
            1 -> return FavFragment()
        }
        return MainFragment()
    }
}