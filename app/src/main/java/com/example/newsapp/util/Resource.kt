package com.example.newsapp.util

//using this class to get the resources like success, error, loading around class
//that is wrapped by this sealed class
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    //Success class is used to get data after success of reterofit response
    class Success<T>(data: T) : Resource<T>(data)

    //Error class is used to get the error message after error in retrofit response
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    //Loading class is used while the MutableLivedata loading the data
    class Loading<T> : Resource<T>()
}