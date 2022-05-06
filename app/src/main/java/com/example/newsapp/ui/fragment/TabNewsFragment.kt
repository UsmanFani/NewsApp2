package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentTabNewsBinding
import com.example.newsapp.ui.adapter.NewsFragmentCollectionAdapter
import com.example.newsapp.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class TabNewsFragment : Fragment(R.layout.fragment_tab_news) {
    private lateinit var newsFragmentCollectionAdapter: NewsFragmentCollectionAdapter
    private var _binding: FragmentTabNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val breakingNewsFragList = listOf(
            BreakingNewsFragment.newInstance(Constants.Category.GENERAL),
            BreakingNewsFragment.newInstance(Constants.Category.TECHNOLOGY),
            BreakingNewsFragment.newInstance(Constants.Category.SCIENCE),
            BreakingNewsFragment.newInstance(Constants.Category.HEALTH),
            BreakingNewsFragment.newInstance(Constants.Category.ENTERTAINMENT),
            BreakingNewsFragment.newInstance(Constants.Category.BUSINESS),
            BreakingNewsFragment.newInstance(Constants.Category.SPORTS)
        )
        newsFragmentCollectionAdapter =
            NewsFragmentCollectionAdapter(childFragmentManager, lifecycle, breakingNewsFragList)
        val pager = binding.pager
        pager.adapter = newsFragmentCollectionAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> Constants.Category.GENERAL
                1 -> Constants.Category.TECHNOLOGY
                2 -> Constants.Category.SCIENCE
                3 -> Constants.Category.HEALTH
                4 -> Constants.Category.ENTERTAINMENT
                5 -> Constants.Category.BUSINESS
                6 -> Constants.Category.SPORTS
                else -> Constants.Category.GENERAL
            }
        }.attach()


    }
}