package com.example.ocs_browser.ui.search

import androidx.lifecycle.ViewModel
import com.example.ocs_browser.models.SearchResults
import com.example.ocs_browser.repositories.ApiRepository
import io.reactivex.rxjava3.subjects.ReplaySubject

class SearchViewModel : ViewModel() {
    private val apiRepository = ApiRepository
    val results: ReplaySubject<SearchResults> = ReplaySubject.create()

    init {
        apiRepository.getResults()
            .subscribe { result -> results.onNext(result) }
    }
}