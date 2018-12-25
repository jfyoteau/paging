package io.github.jfyoteau.paging.core.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ListDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {

    // =====================================================================================
    // region ListDataSource<Key, Item>
    // =====================================================================================

    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Key, Value>) {
        // 1. Set the network status to LOADING
        this.networkState.postValue(NetworkState.LOADING)
        this.initialLoad.postValue(NetworkState.LOADING)

        // 2. Get the data list
        val result = getInitialData(params)

        // 3. Check the data list
        if (result != null) {
            // No error

            // 3.1 Set retry function
            this.retry = null

            // 3.2 Set the network status
            this.networkState.postValue(NetworkState.LOADED)
            this.initialLoad.postValue(NetworkState.LOADED)

            // 3.3 Return the result
            callback.onResult(result.list, null, result.nextKey)
        } else {
            // Error

            // 3.1 Set retry function
            this.retry = {
                loadInitial(params, callback)
            }

            // 3.2 Set the network status
            val error = NetworkState.error("error")
            this.networkState.postValue(error)
            this.initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        // 1. Set the network status to LOADING
        this.networkState.postValue(NetworkState.LOADING)

        // 2. Get the data list
        val result = getNextData(params)

        // 3. Check the data list
        if (result != null) {
            // No error

            // 3.1 Set retry function
            this.retry = null

            // 3.2 Set the network status
            this.networkState.postValue(NetworkState.LOADED)
            this.initialLoad.postValue(NetworkState.LOADED)

            // 3.3 Return the result
            callback.onResult(result.list, result.nextKey)
        } else {
            // Error

            // 3.1 Set retry function
            this.retry = {
                loadAfter(params, callback)
            }

            // 3.2 Set the network status
            val error = NetworkState.error("error")
            this.networkState.postValue(error)
            this.initialLoad.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        // Do nothing
    }

    // endregion ListDataSource<Key, Value>

    // =====================================================================================
    // region Network status
    // =====================================================================================

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    // endregion Network status

    // =====================================================================================
    // region Retry part
    // =====================================================================================

    /**
     * Retry function
     */
    protected var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = this.retry
        this.retry = null
        prevRetry?.let {
            GlobalScope.launch(Dispatchers.IO) {
                it.invoke()
            }
        }
    }

    // endregion Retry part

    data class Result<Key, Value>(
        val list: List<Value>,
        val nextKey: Key?
    )

    abstract fun getInitialData(params: LoadInitialParams<Key>): Result<Key, Value>?

    abstract fun getNextData(params: LoadParams<Key>): Result<Key, Value>?

}
