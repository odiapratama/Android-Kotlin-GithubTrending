package com.problemsolver.githubtrending.ui.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.problemsolver.githubtrending.base.BaseViewModel
import com.problemsolver.githubtrending.models.Trending
import com.problemsolver.githubtrending.services.TrendingRepository
import io.reactivex.schedulers.Schedulers

class TrendingViewModel(private val trendingRepository: TrendingRepository) : BaseViewModel() {

    private var _trendingList = MutableLiveData<List<Trending>>()
    val trendingList: LiveData<List<Trending>> get() = _trendingList

    private var _isResponse = MutableLiveData<Boolean>()
    val isResponse: LiveData<Boolean> get() = _isResponse

    fun getTrendingList() {
        trendingRepository.getTrendingList()
            .observeOn(Schedulers.io())
            .subscribe({
                _trendingList.postValue(it)
                _isResponse.postValue(true)
            }, {
                _isResponse.postValue(false)
                it.printStackTrace()
            }).addToComposite(compositeDisposable)
    }

}