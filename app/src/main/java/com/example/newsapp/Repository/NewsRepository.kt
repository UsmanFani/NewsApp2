package com.example.newsapp.Repository

import com.example.newsapp.Network.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article

//repository class is used to get the data from Local/Remote
class NewsRepository(val database: ArticleDatabase) {


    suspend fun getHeadlineNews(countryCode: String, pageNo: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNo)

    suspend fun getSearchNews(searchText: String, pageNo: Int) =
        RetrofitInstance.api.getSearchNews(searchText, pageNo)

    suspend fun insert(article:Article)=
        database.getArticleDao().articleInsert(article)

    fun getSavedArticles()=
        database.getArticleDao().getAllArticles()

    suspend fun delete(article: Article)=
        database.getArticleDao().deleteArticle(article)
}