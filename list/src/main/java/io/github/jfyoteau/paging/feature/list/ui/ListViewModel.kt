package io.github.jfyoteau.paging.feature.list.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import io.github.jfyoteau.paging.feature.list.repository.MyRepository

class ListViewModel(private val repository: MyRepository) : ViewModel() {

    private val startEvent = MutableLiveData<Void>()
    private val listing = map(startEvent) {
        repository.getItemList(3)
    }

    val pagedList = switchMap(listing) {
        it.pagedList
    }

    val networkState = switchMap(listing) {
        it.networkState
    }

    val refreshState = switchMap(listing) {
        it.refreshState
    }

    fun start() {
        startEvent.value = null
    }

    fun refresh() {
        val listing = this.listing.value ?: return
        listing.refresh()
    }

    fun retry() {
        val listing = this.listing.value ?: return
        listing.retry()
    }

}
