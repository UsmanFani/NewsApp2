package com.example.newsapp.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.ui.viewmodels.NewsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.articleArgs
        Log.d("TAG", article.url.toString())
        val viewModel: NewsViewModel by activityViewModels()
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }
        binding.saveFab.setOnClickListener {
            viewModel.saveNews(article)
            Toast.makeText(activity,"Article Saved",Toast.LENGTH_SHORT).show()
        }
        val frag = activity?.findViewById(R.id.fragConstraint) as ConstraintLayout
    }


    override fun onResume() {
        super.onResume()
        val navigation = activity?.findViewById(R.id.bottomNavView) as BottomNavigationView
        navigation.isVisible = false
        val appBarLayout = activity?.findViewById(R.id.appBarLayout) as AppBarLayout
        appBarLayout.setExpanded(false,true)
       // (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val navigation = activity?.findViewById(R.id.bottomNavView) as BottomNavigationView
        navigation.isVisible = true
        val appBarLayout = activity?.findViewById(R.id.appBarLayout) as AppBarLayout
        appBarLayout.setExpanded(true)
       // (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }
}