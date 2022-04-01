package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

//        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {article->
//            newsAdapter.differ.submitList(article)
//        })

        lifecycleScope.launch {
            viewModel.getSavedArticles().collectLatest {
                newsAdapter.differ.submitList(it)
            }
        }

        //callback for swipe to delete article and passing it to ItemTouchHelper
        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.absoluteAdapterPosition
                val article=newsAdapter.differ.currentList[position]
                viewModel.deleteSavedNews(article)
                Snackbar.make(binding.root,"Article Deleted",Snackbar.LENGTH_SHORT)
                    .setAction("Undo"){
                        viewModel.saveNews(article)
                }.show()
            }

        }
        //attaching this itemTouchhelper to RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.saveRv)

        newsAdapter.setOnItemClickListner {
            val action=SaveNewsFragmentDirections.actionSaveNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
    }

    fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.apply {
            saveRv.adapter = newsAdapter
            saveRv.layoutManager = LinearLayoutManager(activity)
        }
    }
}