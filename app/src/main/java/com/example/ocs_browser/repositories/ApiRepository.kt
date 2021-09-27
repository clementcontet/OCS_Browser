package com.example.ocs_browser.repositories

import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    private val ocsService = Retrofit.Builder()
        .baseUrl("https://api.ocs.fr")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(OcsService::class.java)

    fun getResults(): Single<SearchResults> {
        return ocsService.getResults()
    }
}