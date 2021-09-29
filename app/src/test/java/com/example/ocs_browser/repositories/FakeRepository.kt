package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchItem
import com.example.ocs_browser.models.SearchResult
import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single

object FakeRepository : ApiRepositoryInterface {
    override fun getResults(searchTerm: String): Single<SearchResults> {
        return Single.just(SearchResults(contents = ArrayList()))
    }

    override fun getDetails(detailsPath: String): Single<SearchResult> {
        if (detailsPath == "tv_show") {
            return Single.just(
                SearchResult(
                    contents = SearchItem(
                        pitch = "unread pitch",
                        seasons = listOf(
                            SearchItem.Season(
                                pitch = "tv show pitch"
                            )
                        )
                    )
                )
            )
        } else {
            return Single.just(
                SearchResult(
                    contents = SearchItem(
                        pitch = "movie pitch",
                        seasons = ArrayList()
                    )
                )
            )
        }
    }
}