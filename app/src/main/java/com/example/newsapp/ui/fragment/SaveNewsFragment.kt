package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.viewmodels.NewsViewModel

class SaveNewsFragment:Fragment() {
    var _binding: FragmentSavedNewsBinding? =null
    val binding get() =_binding!!
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding=FragmentSavedNewsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel:NewsViewModel by activityViewModels()
        setupRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {article->
            newsAdapter.differ.submitList(article)
        })

        newsAdapter.setOnItemClickListner {
            val action=SaveNewsFragmentDirections.actionSaveNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
    }

    fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.saveRv.adapter=newsAdapter
        binding.saveRv.layoutManager=LinearLayoutManager(activity)
    }
}