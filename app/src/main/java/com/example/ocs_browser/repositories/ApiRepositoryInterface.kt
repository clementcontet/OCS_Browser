package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchResult
import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single

interface ApiRepositoryInterface {
    fun getResults(searchTerm: String): Single<SearchResults>
    fun getDetails(detailsPath: String): Single<SearchResult>
}