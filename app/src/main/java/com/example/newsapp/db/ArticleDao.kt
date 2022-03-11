package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //to handle the same saved articles by replacing
    suspend fun articleReplace(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>> //LiveData is used to observe the changes in data

    @Delete
    suspend fun deleteArticle(article: Article)

}