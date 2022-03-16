package com.example.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Repository.NewsRepository
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response


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
            //getting retrofit response through repository
            val response = newsRepository.getHeadlineNews(countryCode, headlinePage)
            headlinesLiveData.postValue(handleHeadlinesResponse(response))
        }
    }

    fun getSearchNews(searchNews: String) {
        viewModelScope.launch {
            searchNewsLiveData.postValue(Resource.Loading())
            val response = newsRepository.getSearchNews(searchNews, headlinePage)
            searchNewsLiveData.postValue(searchHeadLineResponse(response))
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

}