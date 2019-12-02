package com.afrasilv.movietrack.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    //region common in viewmodel
    private val job = Job()

    // implement and override coroutineContext for cancel all coroutines
    // if user close the view
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelJob() {
        job.cancel()
    }
    //endregion

}