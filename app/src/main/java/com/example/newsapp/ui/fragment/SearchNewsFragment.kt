package com.example.newsapp.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsLoadStateAdapter
import com.example.newsapp.ui.adapter.NewsPagingAdapter
import com.example.newsapp.ui.viewmodels.NewsViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {
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
        setHasOptionsMenu(true)
        //  (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.flag) {
            lifecycleScope.launch {
                viewModel.getSearchArticles(viewModel.currentChar.toString()).collect {
                    newsAdapter.submitData(it)
                }
            }
        }



        newsAdapter.setOnItemClickListner {
            val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }


    }

    private fun setupRecycler() {
        newsAdapter = NewsPagingAdapter()
        val newAdapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter { newsAdapter.retry() },
            footer = NewsLoadStateAdapter { newsAdapter.retry() }
        )
        binding.searchRv.adapter = newAdapter
        binding.searchRv.layoutManager = LinearLayoutManager(activity)
        val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.searchRv.addItemDecoration(decoration)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.searchNewsFragment).isVisible = false
        inflater.inflate(R.menu.top_app_bar_search, menu)
        val searchItem = menu.findItem(R.id.searchNews)
        searchItem.expandActionView()
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search for News"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("Query", "onQueryTextSubmit:$query ")
                lifecycleScope.launch {
                    if (query != null) {
                        viewModel.getSearchArticles(query).collect {
                            newsAdapter.submitData(it)
                        }
                    }
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onStop() {
        super.onStop()
        val appBarLayout = activity?.findViewById(R.id.appBarLayout) as AppBarLayout
        appBarLayout.setExpanded(true, true)
    }

}