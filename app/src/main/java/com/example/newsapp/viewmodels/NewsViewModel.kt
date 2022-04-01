package com.example.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.NewsRepository
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.sql.SQLException


class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    //mutable data holding and observing
    val headlinesLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinePage: Int = 1

    init {
        getHeadlines("in")
    }

    fun getHeadlines(countryCode: String) {
        viewModelScope.launch {
            headlinesLiveData.postValue(Resource.Loading())
            try {
                //getting retrofit response through repository
                val response = newsRepository.getHeadlineNews(countryCode, headlinePage)
                //headlinesLiveData.postValue(handleHeadlinesResponse(response))
            }catch (e:HttpException){
                Log.e("e_headline", "httpException: $e")
            }
        }
    }

    fun getSearchNews(searchNews: String) {
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            try {
                val response = newsRepository.getSearchNews(searchNews, headlinePage)
              //  searchNewsLiveData.postValue(searchHeadLineResponse(response))
            }catch (e:HttpException){
                Log.e("e_news", "getSearchNews: $e", )
            }
        }
    }

    //this fun is used to handling the retrofit response
    private fun handleHeadlinesResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        //if response is success then get the response body
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        //if response is error then return the response with response message
        return Resource.Error(response.message())
    }

    private fun searchHeadLineResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                return Resource.Success(newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveNews(article: Article) {
        viewModelScope.launch {
            try {
                newsRepository.insert(article)
            }catch (e:SQLException){
                Log.e("e_sql", "saveNews: $e" )
            }
        }
    }

    fun deleteSavedNews(article: Article){
        viewModelScope.launch {
            try {
                newsRepository.delete(article)
            }catch (e:SQLException){
                Log.e("e_sql", "deleteSavedNews: $e", )
            }
        }
    }

    fun getSavedArticles()=newsRepository.getSavedArticles().asFlow()
}