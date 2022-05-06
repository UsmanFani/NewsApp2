package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.ui.viewmodels.NewsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class SaveNewsFragment : Fragment() {
    var _binding: FragmentSavedNewsBinding? = null
    val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: NewsViewModel by activityViewModels()
        setupRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { article ->
            newsAdapter.differ.submitList(article)
        })

//        lifecycleScope.launch {
//            viewModel.getSavedArticles().collectLatest {
//                newsAdapter.differ.submitList(it)
//            }
//        }

        //callback for swipe to delete article and passing it to ItemTouchHelper
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteSavedNews(article)
                Snackbar.make(binding.root, "Article Deleted", Snackbar.LENGTH_SHORT)
                    .setAction("Undo") {
                        viewModel.saveNews(article)
                        binding.saveRv.scrollToPosition(position)
                    }.show()
            }

        }
        //attaching this itemTouchhelper to RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.saveRv)

        newsAdapter.setOnItemClickListner {
            val action = SaveNewsFragmentDirections.actionSaveNewsFragmentToArticleFragment(it)
            findNavController().navigate(action)
        }
        val navigation = activity?.findViewById(R.id.bottomNavView) as BottomNavigationView
        navigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.saveNewsFragment -> binding.saveRv.smoothScrollToPosition(0)
            }
        }
    }

    fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.apply {
            saveRv.adapter = newsAdapter
            saveRv.layoutManager = LinearLayoutManager(activity)
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