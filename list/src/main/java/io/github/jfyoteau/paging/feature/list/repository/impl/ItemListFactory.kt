package io.github.jfyoteau.paging.feature.list.repository.impl

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.jfyoteau.paging.feature.list.repository.Item

internal class ItemListFactory : DataSource.Factory<Int, Item>() {

    val sourceLiveData = MutableLiveData<ItemListDataSource>()

    override fun create(): DataSource<Int, Item> {
        val source = ItemListDataSource()
        sourceLiveData.postValue(source)
        return source
    }

}
