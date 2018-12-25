package io.github.jfyoteau.paging.feature.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import io.github.jfyoteau.paging.feature.databinding.ListItemBinding
import io.github.jfyoteau.paging.feature.list.repository.Item

class ListAdapter : PagedListAdapter<Item, MyListItemViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListItemViewHolder {
        val layoutFlater = LayoutInflater.from(parent.context)
        val dataBinding = ListItemBinding.inflate(layoutFlater, parent, false)
        return MyListItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: MyListItemViewHolder, position: Int) {
        val listItem = getItem(position) ?: return

        holder.dataBinding.name.text = listItem.name
        holder.dataBinding.firstName.text = listItem.firstName
        holder.bindView()
    }

    companion object {

        val ITEM_COMPARATOR = object: DiffUtil.ItemCallback<Item>() {

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }

    }

}
