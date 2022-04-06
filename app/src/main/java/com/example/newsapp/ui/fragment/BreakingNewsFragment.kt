package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsLoadStateAdapter
import com.example.newsapp.adapter.NewsPagingAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.databinding.LoadStateItemBinding
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class BreakingNewsFragment : Fragment() {
    // lateinit var newsAdapter: NewsAdapter
    lateinit var newsAdapter: NewsPagingAdapter
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

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
//        val viewModel: NewsViewModel by activityViewModels()
        val viewModel: NewsViewModel by activityViewModels()
        setupRecycleView()


        newsAdapter.setOnItemClickListner {
            // navigating to Article Fragment by passing Article data class
            val action =
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment2(it)
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
            viewModel.getAllArticles().collectLatest {
                newsAdapter.submitData(it)
            }

            newsAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        view.findViewById<ProgressBar>(R.id.progress_circular).visibility =
                            View.VISIBLE
                    }
                }
            }
        }
    }


    fun setupRecycleView() {
        // newsAdapter = NewsAdapter()
        newsAdapter = NewsPagingAdapter()
        newsAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter(newsAdapter::retry),
            footer = NewsLoadStateAdapter(newsAdapter::retry)
        )
        binding.headlineRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}