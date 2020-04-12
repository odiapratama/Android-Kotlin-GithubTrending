package com.problemsolver.githubtrending.services

import com.problemsolver.githubtrending.data.AppPreferences
import com.problemsolver.githubtrending.models.Trending
import com.problemsolver.githubtrending.utils.setDataValidUntil
import io.reactivex.Observable

class TrendingRepositoryImpl(
    private val trendingApi: TrendingApi,
    private val appPref: AppPreferences
) : TrendingRepository {

    override fun getTrendingList(
        language: String,
        since: String,
        spoken_language_code: String
    ): Observable<List<Trending>> {
        return trendingApi.getTrendingList(
            language,
            since,
            spoken_language_code
        ).flatMap {
            setCacheFetchTime()
            setCacheTrendingList(it)
            Observable.just(it)
        }
    }

    private fun setCacheFetchTime() = appPref.setFetchTime(setDataValidUntil())

    override fun getCacheFetchTime(): String? = appPref.getFetchTime()

    private fun setCacheTrendingList(data: List<Trending>) = appPref.setTrendingList(data)

    override fun getCacheTrendingList(): List<Trending>? = appPref.getTrendingList()

}