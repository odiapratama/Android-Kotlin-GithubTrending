package com.problemsolver.githubtrending.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        with(compositeDisposable) {
            clear()
            dispose()
        }
        super.onCleared()
    }

    fun Disposable.addToComposite(compositeDisposable: CompositeDisposable) =
        compositeDisposable.add(this)

}