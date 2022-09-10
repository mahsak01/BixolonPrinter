package com.example.tianaserver.common

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class TianaCompletableObserver(val compositeDisposable: CompositeDisposable) :
    CompletableObserver {
    override fun onError(e: Throwable) {
        Timber.e(e)
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }
}