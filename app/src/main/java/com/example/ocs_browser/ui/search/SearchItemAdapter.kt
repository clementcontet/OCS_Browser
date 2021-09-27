package com.example.ocs_browser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ocs_browser.R
import com.example.ocs_browser.databinding.ListItemSearchItemBinding
import com.example.ocs_browser.models.SearchResults
import com.example.ocs_browser.ui.search.SearchItemViewModel

class SearchItemAdapter :
    ListAdapter<SearchResults.Content, SearchItemAdapter.ViewHolder>(SearchItemDiffCallback()) {

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
        init {
            binding.setClickListener { view ->
                view.findNavController().navigate(R.id.detailFragment)
            }
        }

        fun bind(content: SearchResults.Content) {
            with(binding) {
                viewModel = SearchItemViewModel(content)
                executePendingBindings()
            }
        }
    }
}

private class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchResults.Content>() {

    override fun areItemsTheSame(
        oldItem: SearchResults.Content,
        newItem: SearchResults.Content
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle
    }

    override fun areContentsTheSame(
        oldItem: SearchResults.Content,
        newItem: SearchResults.Content
    ): Boolean {
        return oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle
    }
}

