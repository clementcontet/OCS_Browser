package com.example.ocs_browser.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ocs_browser.models.SearchItem
import com.example.ocs_browser.repositories.ApiRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class DetailViewModel : ViewModel() {
    private val apiRepository = ApiRepository

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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resultPitch ->
                pitch.value = resultPitch
            }
    }
}
