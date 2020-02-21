package com.afrasilv.movietrack.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.antonioleiva.mymovies.ui.common.Scope
import kotlinx.coroutines.CoroutineDispatcher

open class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Impl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}