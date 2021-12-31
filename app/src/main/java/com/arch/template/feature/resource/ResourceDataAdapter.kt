package com.arch.template.feature.resource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arch.template.R
import com.arch.template.databinding.ItemResourceBinding
import com.core.entity.ResourceData

class ResourceDataAdapter :
    PagingDataAdapter<ResourceData, ResourceDataAdapter.ViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemResourceBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_resource,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemResourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResourceData) {
            with(binding) {
                resourceData = item
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<ResourceData>() {
        override fun areItemsTheSame(oldItem: ResourceData, newItem: ResourceData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResourceData, newItem: ResourceData): Boolean {
            return oldItem == newItem
        }
    }
}