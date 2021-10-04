package com.furkanpasalioglu.newsapp.interfaces

import com.furkanpasalioglu.newsapp.models.News
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("everything")
    fun getNews(
        @Query("q") query: String,
        @Query("page") page:Int,
        @Query("apiKey") apiKey: String): Single<News>
}