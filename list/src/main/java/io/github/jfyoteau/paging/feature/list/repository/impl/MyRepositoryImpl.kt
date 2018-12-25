package io.github.jfyoteau.paging.feature.list.repository.impl

import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import io.github.jfyoteau.paging.core.repository.Listing
import io.github.jfyoteau.paging.feature.list.repository.Item
import io.github.jfyoteau.paging.feature.list.repository.MyRepository

internal class MyRepositoryImpl : MyRepository {

    override fun getItemList(pageSize: Int): Listing<Item> {
        val sourceFactory = ItemListFactory()

        val livePagedList = sourceFactory.toLiveData(pageSize = pageSize)

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initialLoad
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            }
        )
    }

}