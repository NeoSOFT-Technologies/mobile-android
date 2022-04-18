package com.arch.template.ui.feature.resource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arch.presentation.model.ResourceDataPresentation
import com.arch.template.R
import com.arch.template.databinding.ItemResourceBinding

class ResourceDataAdapter :
    PagingDataAdapter<ResourceDataPresentation, ResourceDataAdapter.ViewHolder>(DiffUtilCallBack) {

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
        fun bind(item: ResourceDataPresentation) {
            with(binding) {
                resourceData = item
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<ResourceDataPresentation>() {
        override fun areItemsTheSame(
            oldItem: ResourceDataPresentation,
            newItem: ResourceDataPresentation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResourceDataPresentation,
            newItem: ResourceDataPresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
}