package com.problemsolver.githubtrending.services

import com.problemsolver.githubtrending.models.Trending

interface TrendingRepository {
    val listTrending: List<Trending>?
    fun getTrendingList(listener: TrendingListener<List<Trending>>, language: String = "", since: String = "", spoken_language_code: String = "")
}

interface TrendingListener<T> {
    fun onSuccess(response: T? = null)
    fun onFailed(message: String?)
}