package com.furkanpasalioglu.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.furkanpasalioglu.newsapp.enums.State
import com.furkanpasalioglu.newsapp.interfaces.ApiInterface
import com.furkanpasalioglu.newsapp.models.Article
import com.furkanpasalioglu.newsapp.repository.NewsDataSource
import com.furkanpasalioglu.newsapp.repository.NewsDataSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    apiInterface: ApiInterface
) : ViewModel() {

    var newsList: LiveData<PagedList<Article>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val newsDataSourceFactory: NewsDataSourceFactory =
        NewsDataSourceFactory(compositeDisposable, apiInterface)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(newsDataSourceFactory.newsDataSourceLiveData, NewsDataSource::state)

    fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}