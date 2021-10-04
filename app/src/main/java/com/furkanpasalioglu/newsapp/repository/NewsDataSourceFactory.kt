package com.furkanpasalioglu.newsapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.furkanpasalioglu.newsapp.interfaces.ApiInterface
import com.furkanpasalioglu.newsapp.models.Article
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsDataSourceFactory @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val apiInterface: ApiInterface
) : DataSource.Factory<Int, Article>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, Article> {
        val newsDataSource = NewsDataSource(apiInterface, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}