package com.example.ocs_browser.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.ocs_browser.adapters.SearchItemAdapter
import com.example.ocs_browser.databinding.FragmentSearchBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        val adapter = SearchItemAdapter()
        binding.resultList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: SearchItemAdapter) {
        viewModel.results
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> adapter.submitList(result.contents) }
    }
}