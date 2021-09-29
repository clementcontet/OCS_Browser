package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single

object FakeRepository: ApiRepositoryInterface {
    override fun getResults(searchTerm: String): Single<SearchResults> {
        return Single.just(SearchResults(contents = ArrayList()))
    }
}