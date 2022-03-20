package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.viewmodels.NewsViewModel

class ArticleFragment : Fragment() {
    private var _binding:FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val args:ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article=args.articleArgs
        Log.d("TAG", article.url.toString())
        val viewModel:NewsViewModel by activityViewModels()
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url.toString())
        }

    }
}