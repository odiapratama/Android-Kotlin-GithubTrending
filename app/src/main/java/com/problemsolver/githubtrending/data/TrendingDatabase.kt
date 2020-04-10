package com.problemsolver.githubtrending.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.problemsolver.githubtrending.models.Trending
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class TrendingDatabase(context: Context) {

    private val privateMode = 0
    private val prefName = "TrendingDatabase"
    private val trendingList = "TrendingList"
    private val fetchTime = "FetchTime"
    private val pref: SharedPreferences? = context.getSharedPreferences(prefName, privateMode)

    fun setTrendingList(data: List<Trending>?) {
        data?.let {
            pref?.edit {
                putString(trendingList, Gson().toJson(it))
            }
        }
    }

    fun getTrendingList(): List<Trending>? {
        val listType: Type = object : TypeToken<List<Trending>>() {}.type
        return Gson().fromJson(pref?.getString(trendingList, null), listType)
    }

    fun setFetchTime(time: String?) {
        time?.let {
            pref?.edit {
                putString(fetchTime, it)
            }
        }
    }

    fun getFetchTime(): String? {
        return pref?.getString(fetchTime, null)
    }
}