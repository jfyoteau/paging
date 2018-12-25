package io.github.jfyoteau.paging.feature.list.repository.impl

import io.github.jfyoteau.paging.core.repository.ListDataSource
import io.github.jfyoteau.paging.feature.list.repository.Item
import java.util.*

internal class ItemListDataSource : ListDataSource<Int, Item>() {

    // =====================================================================================
    // region ListDataSource<Int, Item>
    // =====================================================================================

    override fun getInitialData(params: LoadInitialParams<Int>): Result<Int, Item>? {
        val startIndex = 0
        val endIndex = params.requestedLoadSize
        val resultList = getData(startIndex, endIndex)
        val nextIndex: Int? = if (resultList.size < params.requestedLoadSize) null else endIndex
        return Result(resultList, nextIndex)
    }

    override fun getNextData(params: LoadParams<Int>): Result<Int, Item>? {
        val startIndex = params.key
        val endIndex = startIndex + params.requestedLoadSize
        val resultList = getData(startIndex, endIndex)
        val nextIndex: Int? = if (resultList.size < params.requestedLoadSize) null else endIndex
        return Result(resultList, nextIndex)
    }

    // endregion ListDataSource<Int, Item>

    private fun getData(startIndex: Int, endIndex: Int): List<Item> {
        val resultList = ArrayList<Item>()
        for (i in startIndex until endIndex) {
            if (i < 32) {
                break
            }
            resultList.add(Item("name ${i + 1}", "fist name ${i + 1}"))
        }

        return resultList
    }

}
