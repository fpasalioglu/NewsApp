package com.furkanpasalioglu.newsapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.furkanpasalioglu.newsapp.di.NetworkModule.apiKey
import com.furkanpasalioglu.newsapp.di.NetworkModule.query
import com.furkanpasalioglu.newsapp.enums.State
import com.furkanpasalioglu.newsapp.interfaces.ApiInterface
import com.furkanpasalioglu.newsapp.models.Article
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            apiInterface.getNews(page = 1, apiKey = apiKey, query = query)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.articles,
                            null,
                            2
                        )
                    }
                ) {
                    updateState(State.ERROR)
                    setRetry { loadInitial(params, callback) }
                }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            apiInterface.getNews(page = params.key, apiKey = apiKey, query = query)
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(response.articles,
                            params.key + 1
                        )
                    }
                ) {
                    updateState(State.ERROR)
                    setRetry { loadAfter(params, callback) }
                }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}