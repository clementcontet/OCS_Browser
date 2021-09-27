package com.example.ocs_browser.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ocs_browser.models.SearchResults
import com.example.ocs_browser.repositories.ApiRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

class SearchViewModel : ViewModel() {
    private val apiRepository = ApiRepository
    val results: ReplaySubject<SearchResults> = ReplaySubject.create()
    var searchTerm: MutableLiveData<String> = MutableLiveData()
    var searchTermRx: Flowable<String>? = null
        set(value) {
            field = value
            listenForSearchTeam()
        }
    var gridVisible: LiveData<Boolean> =
        Transformations.map(searchTerm) { searchTerm -> searchTerm.length >= 3 }

    fun listenForSearchTeam() {
        searchTermRx!!
            .skip(1)
            .filter { searchTerm -> searchTerm.length >= 3 }
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchTerm -> apiRepository.getResults(searchTerm).toFlowable() }
            .subscribe { result -> results.onNext(result) }
    }
}