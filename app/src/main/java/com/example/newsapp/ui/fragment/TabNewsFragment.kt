package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentTabNewsBinding
import com.example.newsapp.ui.adapter.NewsFragmentCollectionAdapter
import com.example.newsapp.util.Constants
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

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
            BreakingNewsFragment(Constants.Category.GENERAL),
            BreakingNewsFragment(Constants.Category.TECHNOLOGY),
            BreakingNewsFragment(Constants.Category.SCIENCE),
            BreakingNewsFragment(Constants.Category.HEALTH),
            BreakingNewsFragment(Constants.Category.ENTERTAINMENT),
            BreakingNewsFragment(Constants.Category.BUSINESS),
            BreakingNewsFragment(Constants.Category.SPORTS)
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