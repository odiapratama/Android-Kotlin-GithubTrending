package com.problemsolver.githubtrending.modules

import com.problemsolver.githubtrending.data.AppPreferences
import com.problemsolver.githubtrending.services.API_PATH
import com.problemsolver.githubtrending.services.TrendingApi
import com.problemsolver.githubtrending.services.TrendingRepository
import com.problemsolver.githubtrending.services.TrendingRepositoryImpl
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val serviceModule = module {
    single { createOkHttpClient() }
    single { createApi<TrendingApi>(API_PATH, get(), getProperty("github_trend_api")) }
    single { AppPreferences(get()) }
    single<TrendingRepository> { TrendingRepositoryImpl(get(), get()) }
}

private fun createOkHttpClient(): OkHttpClient {
    val timeout = 10L
    return OkHttpClient.Builder()
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()
}

private inline fun <reified T> createApi(
    servicePath: String,
    okHttpClient: OkHttpClient,
    baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl + servicePath)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}