package com.example.newsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.ui.fragment.BreakingNewsFragment
import com.example.newsapp.util.Constants

class NewsFragmentCollectionAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val breakingNewsFragList: List<BreakingNewsFragment>
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {

        return breakingNewsFragList[position]

    }
}