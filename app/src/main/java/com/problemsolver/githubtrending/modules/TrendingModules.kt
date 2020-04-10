package com.problemsolver.githubtrending.modules

import com.problemsolver.githubtrending.ui.trending.TrendingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trendingModule = module {
    viewModel { TrendingViewModel(get()) }
}