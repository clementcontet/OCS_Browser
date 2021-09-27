package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchResult
import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface OcsService {
    @GET("/apps/v2/contents?search=title%3DGame")
    fun getResults(): Single<SearchResults>

    @GET("{detailsPath}")
    fun getDetails(@Path("detailsPath", encoded = true) detailsPath: String): Single<SearchResult>
}