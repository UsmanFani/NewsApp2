package com.example.newsapp.Repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.Network.NewsApiService
import com.example.newsapp.model.Article
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class NewsHeadlinePagingSource (private val newsApiService: NewsApiService): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article>{
        return try {
            val nextPage=params.key ?: 1
            val response=newsApiService.getHeadlines(pageNumber = nextPage)
            val items=response.articles
            val nextKey = if (items.isEmpty()){
                null
            }else{
                nextPage.plus(1)
            }
            LoadResult.Page(
                data = items,
                prevKey = if(nextPage == 1) null else nextPage.minus(1),
                nextKey
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }catch (e:UnknownHostException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return  state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}