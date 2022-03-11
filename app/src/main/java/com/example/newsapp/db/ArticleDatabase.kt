package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Article
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converter::class)
 abstract class ArticleDatabase:RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile //to make visible to other thread so other thread can't make instance
        private var instance: ArticleDatabase? = null

        //get instance of database to used in the project
        fun getInstance(context: Context): ArticleDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            kotlin.synchronized(this) {
                val insta = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_db"
                ).build()

                instance = insta
                return instance as ArticleDatabase
            }
        }
    }
}