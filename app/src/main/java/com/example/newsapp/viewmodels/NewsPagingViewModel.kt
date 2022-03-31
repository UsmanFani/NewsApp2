package com.example.newsapp.viewmodels

import android.app.DownloadManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.Repository.NewsHeadlinePagingSource
import com.example.newsapp.Repository.NewsSearchPagingSource
import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

class NewsPagingViewModel:ViewModel() {

    fun getAllArticles(): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(10, enablePlaceholders = false  )
    ){
        NewsHeadlinePagingSource(RetrofitInstance.api)
    }.flow.cachedIn(viewModelScope)

    fun getSearchArticles(query: String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(10, enablePlaceholders = false)
    ){
        NewsSearchPagingSource(RetrofitInstance.api,query)
    }.flow.cachedIn(viewModelScope)
}