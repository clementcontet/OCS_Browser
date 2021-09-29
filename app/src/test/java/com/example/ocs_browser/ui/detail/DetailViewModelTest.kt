package com.example.ocs_browser.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ocs_browser.models.SearchItem
import com.example.ocs_browser.repositories.FakeRepository
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class DetailViewModelTest {
    private val testScheduler = TestScheduler()
    private val viewModel = DetailViewModel()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        viewModel.apiRepository = FakeRepository
        viewModel.mainThreadScheduler = testScheduler
    }

    @Test
    fun loadPitch_tvShow_readFirstSeason() {
        viewModel.searchItem = SearchItem(title= listOf(SearchItem.Title()) ,detaillink = "tv_show")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        assertEquals("tv show pitch", viewModel.pitch.value)
    }

    @Test
    fun loadPitch_movie_readRootPitch() {
        viewModel.searchItem = SearchItem(title= listOf(SearchItem.Title()), detaillink = "movie")
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        assertEquals("movie pitch", viewModel.pitch.value)
    }
}
