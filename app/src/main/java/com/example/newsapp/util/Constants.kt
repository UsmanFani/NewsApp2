package com.example.newsapp.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://newsapi.org"
    }

    fun convertTime(publishedAt: String?): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("IST")

        val time = sdf.parse(publishedAt).time
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
        return ago.toString()
    }

    class Category {
        companion object {
            const val BUSINESS = "business"
            const val ENTERTAINMENT = "entertainment"
            const val GENERAL = "general"
            const val HEALTH = "health"
            const val SCIENCE = "science"
            const val SPORTS = "sports"
            const val TECHNOLOGY = "technology"
        }

    }
}