package com.example.newsapp.Repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase

//repository class is used to get the data from Local/Remote
class NewsRepository(database: ArticleDatabase) {


    suspend fun getHeadlineNews(countryCode: String, pageNo: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNo)

    suspend fun getSearchNews(searchText: String, pageNo: Int) =
        RetrofitInstance.api.getSearchNews(searchText, pageNo)

}