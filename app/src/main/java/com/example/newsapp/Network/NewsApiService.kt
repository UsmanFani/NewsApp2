package com.example.newsapp.Network

import com.example.newsapp.BuildConfig
import com.example.newsapp.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

//defining api
//to get the Queries
interface NewsApiService {

    //this GET annotation use to Get the headlines
    @GET("/v2/top-headlines") //@GET(end-point)
    suspend fun getHeadlines(
        @Query("country")  //running Query
        countryCode: String = "in",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): NewsResponse //returning Response of type NewsResponse Data Class. Response<T(Generic Type)>

    @GET("/v2/top-headlines")
    suspend fun getHeadlinesByCategory(
        @Query("country")
        countryCode: String = "in",
        @Query("category")
        headlineCategory : String = "technology",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ):NewsResponse

    @GET("/v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): NewsResponse


}