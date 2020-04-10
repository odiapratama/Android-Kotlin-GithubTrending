package com.problemsolver.githubtrending.services

import com.problemsolver.githubtrending.models.Trending
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingRepositoryImpl(private val trendingApi: TrendingApi) : TrendingRepository {
    private var _listTrending: List<Trending>? = null
    override val listTrending: List<Trending>?
        get() = _listTrending

    override fun getTrendingList(
        listener: TrendingListener<List<Trending>>,
        language: String,
        since: String,
        spoken_language_code: String
    ) {
        trendingApi.getTrendingList(language, since, spoken_language_code)
            .enqueue(object : Callback<List<Trending>> {
                override fun onFailure(call: Call<List<Trending>>, t: Throwable) {
                    listener.onFailed(t.message)
                }

                override fun onResponse(
                    call: Call<List<Trending>>,
                    response: Response<List<Trending>>
                ) {
                    if (response.isSuccessful) {
                        _listTrending = response.body()
                        listener.onSuccess(response.body())
                    } else {
                        listener.onFailed(response.message())
                    }
                }
            })
    }
}