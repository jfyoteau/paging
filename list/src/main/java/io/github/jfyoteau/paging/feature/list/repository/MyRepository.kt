package io.github.jfyoteau.paging.feature.list.repository

import io.github.jfyoteau.paging.core.repository.Listing

interface MyRepository {

    fun getItemList(pageSize: Int): Listing<Item>

}
