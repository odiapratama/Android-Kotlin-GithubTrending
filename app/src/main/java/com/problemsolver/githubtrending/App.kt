package com.problemsolver.githubtrending

import android.app.Application
import com.problemsolver.githubtrending.modules.serviceModule
import com.problemsolver.githubtrending.modules.trendingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(serviceModule, trendingModule))
            properties(
                mapOf("github_trend_api" to "https://github-trending-api.now.sh/")
            )
        }
    }
}