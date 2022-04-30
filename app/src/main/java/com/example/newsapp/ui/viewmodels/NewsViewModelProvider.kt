package com.example.newsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Repository.NewsRepository

//Viewmodelprovider is used to provide modified ViewModel in which we are passing
//the argument in the primary constructor
class NewsViewModelProvider(private val newsRepository: NewsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}