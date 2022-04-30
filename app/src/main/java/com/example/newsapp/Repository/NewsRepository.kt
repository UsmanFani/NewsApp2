package com.example.newsapp.Repository

import android.graphics.pdf.PdfDocument
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.Network.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import kotlinx.coroutines.flow.Flow

//repository class is used to get the data from Local/Remote
class NewsRepository(val database: ArticleDatabase) {


    suspend fun getHeadlineNews(countryCode: String, pageNo: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNo)

    suspend fun getSearchNews(searchText: String, pageNo: Int) =
        RetrofitInstance.api.getSearchNews(searchText, pageNo)

    suspend fun insert(article: Article) =
        database.getArticleDao().articleInsert(article)

    fun getSavedArticles() =
        database.getArticleDao().getAllArticles()


    suspend fun delete(article: Article) =
        database.getArticleDao().deleteArticle(article)

    fun getSearchArticles(query: String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(10)
    ) {
        NewsSearchPagingSource(RetrofitInstance.api, query)
    }.flow

    fun getAllArticles(category:String): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(10)
    ) {
        NewsHeadlinePagingSource(RetrofitInstance.api,category)
    }.flow
}