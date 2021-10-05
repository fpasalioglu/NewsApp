package com.furkanpasalioglu.newsapp.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanpasalioglu.newsapp.models.Article

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles")
    fun getAllNews(): LiveData<List<Article>>

    @Query("SELECT * FROM articles")
    fun getAllNewsPaging(): DataSource.Factory<Int, Article>

    @Query("SELECT * FROM articles WHERE title == :title")
    fun getNews(title: String): Boolean

    @Insert
    fun insert(article: Article)

    @Query("DELETE FROM articles WHERE title = :title")
    fun delete(title: String)
}