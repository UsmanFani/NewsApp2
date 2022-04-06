package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsPagingAdapter
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {
   // lateinit var newsAdapter: NewsAdapter
    lateinit var newsAdapter: NewsPagingAdapter
    var _binding: FragmentSearchNewsBinding? = null
    val binding get() = _binding!!
    val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecycler()
        super.onViewCreated(view, savedInstanceState)


        if(viewModel.flag){
            lifecycleScope.launch {
                viewModel.getSearchArticles(viewModel.currentChar.toString()).collect {
                    newsAdapter.submitData(it)
                }
            }
        }

        binding.searchEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                lifecycleScope.launch {
                    viewModel.getSearchArticles(v.text.toString()).collect {
                        newsAdapter.submitData(it)
                    }
                }

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false

        }


        //to do background job
        //runs inside coroutines scope
    /*    var job: Job? = null
        binding.searchEt.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                if (editable.toString().isNotEmpty()) {
                   // viewModel.getSearchNews(editable.toString())
                    viewModel.getSearchArticles(editable.toString()).collectLatest {
                        newsAdapter.submitData(it)
                    }

                }
            }
        }*/

        //observing the data change
//        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    resource.data?.let { newsResponse ->
//                        newsAdapter.differ.submitList(newsResponse.articles)
//                    }
//
//                }
//                is Resource.Error -> {
//                    resource.message?.let {
//                        Log.e("searchError", it)
//                    }
//                }
//            }
//
//        })

        newsAdapter.setOnItemClickListner {
            val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }



    }

    private fun setupRecycler() {
        newsAdapter = NewsPagingAdapter()
        binding.searchRv.adapter = newsAdapter
        binding.searchRv.layoutManager = LinearLayoutManager(activity)
        val deccoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
        binding.searchRv.addItemDecoration(deccoration)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}