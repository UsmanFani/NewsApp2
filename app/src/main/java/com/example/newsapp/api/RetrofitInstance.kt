package com.example.newsapp.api

import com.example.newsapp.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {

    //creating Singleton of Retrofit to get this instance from anywhere in this project
    companion object{
        //by lazy is used to initialize when it is accessed
        private val retrofit by lazy {
            val logging=HttpLoggingInterceptor() //to log Http Request and Response
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client=OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            //using RetrofitBuilder to create the api
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            }
        //using Retrofit.Builder to create the api
        val api by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }
}