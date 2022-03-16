package com.example.newsapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.newsapp.R
import com.example.newsapp.viewmodels.NewsViewModel

class SaveNewsFragment:Fragment(R.layout.fragment_saved_news) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel:NewsViewModel by activityViewModels()
    }
}