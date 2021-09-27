package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface OcsService {
    @GET("/apps/v2/contents?search=title%3DGame")
    fun getResults(): Single<SearchResults>
}