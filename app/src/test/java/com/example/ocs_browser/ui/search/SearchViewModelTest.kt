package com.example.ocs_browser.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ocs_browser.models.SearchResults
import com.example.ocs_browser.repositories.FakeRepository
import io.reactivex.rxjava3.core.BackpressureStrategy
import org.junit.Test
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Rule

class SearchViewModelTest {
    private val testScheduler = TestScheduler()
    private val subscriber = TestSubscriber<SearchResults>()
    private val searchTermSubject: Subject<String> = PublishSubject.create()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        val viewModel = SearchViewModel()
        viewModel.apiRepository = FakeRepository
        viewModel.ioScheduler = testScheduler
        viewModel.mainThreadScheduler = testScheduler
        viewModel.searchTermRx = searchTermSubject.toFlowable(BackpressureStrategy.BUFFER)
        viewModel.results.toFlowable(BackpressureStrategy.BUFFER).subscribe(subscriber)
    }

    @Test
    fun listenForSearchTeam_empty_firstItemIsSkipped() {
        searchTermSubject.onNext("")
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(0)
    }

    @Test
    fun listenForSearchTeam_longEnough_triggersSearch() {
        searchTermSubject.onNext("")
        searchTermSubject.onNext("gam")
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(1)
    }

    @Test
    fun listenForSearchTeam_tooShort_doesntTriggerSearch() {
        searchTermSubject.onNext("")
        searchTermSubject.onNext("ga")
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(0)
    }

    @Test
    fun listenForSearchTeam_tooQuickly_doesntReTriggerSearch() {
        searchTermSubject.onNext("")
        searchTermSubject.onNext("gam")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        searchTermSubject.onNext("game")
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(1)
    }

    @Test
    fun listenForSearchTeam_slowEnough_reTriggerSearch() {
        searchTermSubject.onNext("")
        searchTermSubject.onNext("gam")
        testScheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS)
        searchTermSubject.onNext("game")
        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(2)
    }
}
