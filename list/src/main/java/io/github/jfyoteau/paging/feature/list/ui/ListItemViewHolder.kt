package io.github.jfyoteau.paging.feature.list.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class ListItemViewHolder<out B: ViewDataBinding>(val dataBinding: B) : RecyclerView.ViewHolder(dataBinding.root) {

    fun bindView() {
        this.dataBinding.executePendingBindings()
    }

}
