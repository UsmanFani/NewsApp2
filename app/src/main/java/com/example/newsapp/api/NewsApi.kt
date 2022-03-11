package com.example.newsapp.api

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//defining api
//to get the Queries
interface NewsApi {

    //this GET annotation use to Get the headlines
    @GET("/v2/top-headlines") //@GET(end-point)
    suspend fun getHeadlines(
        @Query("country")  //running Query
        countryCode: String = "in",
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey:String=API_KEY
        ):Response<NewsResponse> //returning Response of type NewsResponse Data Class. Response<T(Generic Type)>


    @GET("/v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        pageNumber:Int=1,
        @Query("apiKey")
        apiKey: String= API_KEY
    ):Response<NewsResponse>
}