package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.util.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {
    lateinit var newsAdapter: NewsAdapter
    var _binding: FragmentSearchNewsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: NewsViewModel by activityViewModels()
        setupRecycler()

        //to do background job
        //runs inside coroutines scope
        var job: Job? = null
        binding.searchEt.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                if (editable.toString().isNotEmpty()) {
                    viewModel.getSearchNews(editable.toString())
                }
            }
        }

        //observing the data change
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }

                }
                is Resource.Error -> {
                    resource.message?.let {
                        Log.e("searchError", it)
                    }
                }
            }

        })

        newsAdapter.setOnItemClickListner {
            val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setupRecycler() {
        newsAdapter = NewsAdapter()
        binding.searchRv.adapter = newsAdapter
        binding.searchRv.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}