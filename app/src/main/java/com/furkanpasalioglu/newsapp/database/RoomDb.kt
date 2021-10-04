package com.furkanpasalioglu.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanpasalioglu.newsapp.database.dao.ArticlesDao
import com.furkanpasalioglu.newsapp.models.Article

@Database(entities = [Article::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun articlesDao() : ArticlesDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb? {
            if (INSTANCE == null) {
                synchronized(RoomDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomDb::class.java, "article"
                        ).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE
        }

    }
}