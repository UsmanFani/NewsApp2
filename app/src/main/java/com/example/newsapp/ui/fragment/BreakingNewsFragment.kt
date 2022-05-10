package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.adapter.NewsLoadStateAdapter
import com.example.newsapp.ui.adapter.NewsPagingAdapter
import com.example.newsapp.ui.viewmodels.NewsViewModel
import com.example.newsapp.util.Constants
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BreakingNewsFragment() : Fragment() {
    // lateinit var newsAdapter: NewsAdapter
    private lateinit var newsAdapter: NewsPagingAdapter
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance(category: String): BreakingNewsFragment {
            return BreakingNewsFragment().apply {
                arguments = Bundle().apply {
                    putString("category", category)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.getString("category")
        val viewModel: NewsViewModel by activityViewModels()
        setupRecycleView()
        binding.onSwipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                newsAdapter.refresh()
                delay(1000L)
                binding.onSwipeRefresh.isRefreshing = false
            }
        }


        newsAdapter.setOnItemClickListner {
            // navigating to Article Fragment by passing Article data class
            val action =
                TabNewsFragmentDirections.actionTabNewsFragmentToArticleFragment(it)
//            val bundle = Bundle().apply {
//               putParcelable("article", it)
//            }
            //    navigating to given action
            findNavController().navigate(action)
        }

        //attching the observer with this MutableLiveData to observe the data change
//        viewModel.headlinesLiveData.observe(viewLifecycleOwner, Observer { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    resource.data?.let { newsResponse ->
//                        newsAdapter.differ.submitList(newsResponse.articles)
//                    }
//                }
//                is Resource.Error -> {
//                    resource.message?.let { message ->
//                        Log.e("response", message)
//                    }
//                }
//            }
//        })

        lifecycleScope.launch {
            val categori = arguments?.getString("category")
            categori?.let {
                viewModel.getAllArticles(it).collectLatest {
                    newsAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            newsAdapter.loadStateFlow.collectLatest {
                binding.apply {
                    onSwipeRefresh.isRefreshing = it.source.refresh is LoadState.Loading
                    errorIv.isVisible = it.source.refresh is LoadState.Error
                    errorTv.isVisible = it.source.refresh is LoadState.Error
                    errorBtn.isVisible = it.source.refresh is LoadState.Error
                }
            }
        }

        binding.errorBtn.setOnClickListener {
            newsAdapter.refresh()
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                private var check: Boolean = false
                override fun handleOnBackPressed() {
                    if (check) activity?.onBackPressed()
                    else binding.headlineRv.smoothScrollToPosition(0)
                    check = true
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(),"Back Press Again To Exit",Toast.LENGTH_SHORT).show()
                        delay(1000)
                        check = false
                    }
                }

            })
        val navigation = activity?.findViewById(R.id.bottomNavView) as BottomNavigationView
        navigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.tabNewsFragment -> binding.headlineRv.scrollToPosition(0)
            }
        }
    }


    fun setupRecycleView() {
        // newsAdapter = NewsAdapter()
        newsAdapter = NewsPagingAdapter()
        val newAdapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { newsAdapter.retry() },
            footer = NewsLoadStateAdapter { newsAdapter.retry() }
        )
        binding.headlineRv.apply {
            adapter = newAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onStop() {
        super.onStop()
        val appBarLayout = activity?.findViewById(R.id.appBarLayout) as AppBarLayout
        appBarLayout.setExpanded(true,true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}