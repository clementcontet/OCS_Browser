package com.example.ocs_browser.repositories

import android.util.Log
import com.example.ocs_browser.models.SearchResult
import com.example.ocs_browser.models.SearchResults
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository: ApiRepositoryInterface {
    private val ocsService = Retrofit.Builder()
        .baseUrl("https://api.ocs.fr")
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(OcsService::class.java)

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("|", message)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }

    override fun getResults(searchTerm: String): Single<SearchResults> {
        return ocsService.getResults("title=$searchTerm")
    }

    override fun getDetails(detailsPath: String): Single<SearchResult> {
        return ocsService.getDetails(detailsPath)
    }
}