package com.problemsolver.githubtrending.ui.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.problemsolver.githubtrending.models.Trending
import com.problemsolver.githubtrending.services.TrendingListener
import com.problemsolver.githubtrending.services.TrendingRepository

class TrendingViewModel(private val trendingRepository: TrendingRepository): ViewModel() {

    private var _trendingList = MutableLiveData<List<Trending>>()
    val trendingList: LiveData<List<Trending>> get() = _trendingList

    private var _isResponse = MutableLiveData<Boolean>()
    val isResponse: LiveData<Boolean> get() = _isResponse

    fun getTrendingList() {
        trendingRepository.getTrendingList(object : TrendingListener<List<Trending>> {
            override fun onSuccess(response: List<Trending>?) {
                _isResponse.value = true
                _trendingList.value = response
            }

            override fun onFailed(message: String?) {
                _isResponse.value = false
            }
        })
    }
}