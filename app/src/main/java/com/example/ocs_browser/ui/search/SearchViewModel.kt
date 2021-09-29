package com.example.ocs_browser.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ocs_browser.models.SearchResults
import com.example.ocs_browser.repositories.ApiRepositoryInterface
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SearchViewModel : ViewModel() {
    lateinit var apiRepository: ApiRepositoryInterface
    lateinit var ioScheduler: Scheduler
    lateinit var mainThreadScheduler: Scheduler
    val results: BehaviorSubject<SearchResults> = BehaviorSubject.create()
    var searchTerm: MutableLiveData<String> = MutableLiveData()
    var searchTermRx: Flowable<String>? = null
        set(value) {
            field = value
            listenForSearchTeam()
        }
    var gridVisible: LiveData<Boolean> =
        Transformations.map(searchTerm) { searchTerm -> searchTerm.length >= 3 }

    var spinnerVisible: MutableLiveData<Boolean> = MutableLiveData(false)

    private fun listenForSearchTeam() {
        searchTermRx!!
            .skip(1) // searchTermRx always first emit the empty string
            .filter { searchTerm -> searchTerm.length >= 3 } // don't search for too short string
            .observeOn(mainThreadScheduler)
            .doOnNext { spinnerVisible.value = true }
            .debounce(500, TimeUnit.MILLISECONDS, ioScheduler) // 0.5secs between searchs
            .switchMap { searchTerm -> apiRepository.getResults(searchTerm).toFlowable() }
            .observeOn(mainThreadScheduler)
            .doOnNext { spinnerVisible.value = false }
            .subscribe { result -> results.onNext(result) }
    }
}