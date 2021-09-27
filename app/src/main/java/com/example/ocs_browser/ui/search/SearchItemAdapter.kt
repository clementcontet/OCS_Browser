package com.example.ocs_browser.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ocs_browser.R
import com.example.ocs_browser.databinding.ListItemSearchItemBinding
import com.example.ocs_browser.models.SearchItem

class SearchItemAdapter :
    ListAdapter<SearchItem, SearchItemAdapter.ViewHolder>(SearchItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_search_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchItem: SearchItem) {
            with(binding) {
                this.root.setOnClickListener { view ->
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(searchItem)
                    view.findNavController().navigate(action)
                }

                viewModel = SearchItemViewModel(searchItem)
                executePendingBindings()
            }
        }
    }
}

private class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {

    override fun areItemsTheSame(
        oldItem: SearchItem,
        newItem: SearchItem
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle
    }

    override fun areContentsTheSame(
        oldItem: SearchItem,
        newItem: SearchItem
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle
    }
}

