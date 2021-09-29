package com.example.ocs_browser.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ocs_browser.models.SearchItem
import com.example.ocs_browser.repositories.ApiRepositoryInterface
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class DetailViewModel : ViewModel() {
    lateinit var apiRepository: ApiRepositoryInterface
    lateinit var mainThreadScheduler: Scheduler

    var imageUrl: MutableLiveData<String?> = MutableLiveData()
    var title: MutableLiveData<String?> = MutableLiveData()
    var subtitle: MutableLiveData<String?> = MutableLiveData()
    var pitch: MutableLiveData<String?> = MutableLiveData()

    var searchItem: SearchItem? = null
        set(value) {
            if (searchItem == null) {
                field = value
                imageUrl.value = "https://statics.ocs.fr" + value?.fullscreenimageurl
                title.value = value?.title?.get(0)?.value
                subtitle.value = value?.subtitle
                value?.detaillink?.let { loadPitch(it) }
            }
        }

    private fun loadPitch(detailsPath: String) {
        apiRepository.getDetails(detailsPath)
            .map { result ->
                if (!result.contents.seasons.isNullOrEmpty()) {
                    return@map result.contents.seasons[0].pitch ?: ""
                } else {
                    return@map result.contents.pitch ?: ""
                }
            }
            .onErrorResumeNext { Single.just("") }
            .observeOn(mainThreadScheduler)
            .subscribe { resultPitch -> pitch.value = resultPitch }
    }
}
