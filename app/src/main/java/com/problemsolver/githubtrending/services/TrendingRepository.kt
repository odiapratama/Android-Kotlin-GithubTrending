package com.problemsolver.githubtrending.services

import com.problemsolver.githubtrending.models.Trending
import io.reactivex.Observable

interface TrendingRepository {

    fun getCacheFetchTime(): String?

    fun getCacheTrendingList(): List<Trending>?

    fun getTrendingList(
        language: String = "",
        since: String = "",
        spoken_language_code: String = ""
    ): Observable<List<Trending>>

}