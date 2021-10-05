package com.furkanpasalioglu.newsapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.furkanpasalioglu.newsapp.database.RoomDb
import com.furkanpasalioglu.newsapp.database.dao.ArticlesDao
import com.furkanpasalioglu.newsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    application: Application
): ViewModel() {
    private var articlesDao : ArticlesDao

    init {
        val db = RoomDb.getDatabase(application)
        articlesDao = db?.articlesDao()!!
    }

    val newsList: LiveData<PagedList<Article>> = articlesDao.getAllNewsPaging().toLiveData(pageSize = 10)
}