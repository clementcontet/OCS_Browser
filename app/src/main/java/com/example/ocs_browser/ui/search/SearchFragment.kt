package com.example.ocs_browser.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.ocs_browser.R
import com.example.ocs_browser.databinding.FragmentSearchBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var viewModelSubscription: Disposable? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(activity as AppCompatActivity, findNavController())

        val adapter = SearchItemAdapter()
        binding.resultList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        viewModelSubscription?.dispose()
        super.onDestroyView()
    }

    private fun subscribeUi(adapter: SearchItemAdapter) {
        viewModel.searchTermRx =
            Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(this, viewModel.searchTerm))

        viewModelSubscription = viewModel.results
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> adapter.submitList(result.contents) }
    }
}
