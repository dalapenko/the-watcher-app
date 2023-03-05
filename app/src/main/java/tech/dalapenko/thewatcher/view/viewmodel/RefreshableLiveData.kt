package tech.dalapenko.thewatcher.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class RefreshableLiveData<T>(
    private val source: () -> LiveData<T>
) : MediatorLiveData<T>() {

    private var liveData = source.invoke()

    init {
        addSource(liveData, ::observer)
    }

    private fun observer(data: T) {
        value = data
    }

    fun refresh() {
        removeSource(liveData)
        liveData = source.invoke()
        addSource(source.invoke(), ::observer)
    }


}