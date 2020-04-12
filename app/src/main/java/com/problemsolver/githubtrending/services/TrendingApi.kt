package com.problemsolver.githubtrending.services

import com.problemsolver.githubtrending.models.Trending
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_PATH = ""

interface TrendingApi {

    @GET("repositories")
    fun getTrendingList(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spoken_language_code: String
    ): Observable<List<Trending>>

}