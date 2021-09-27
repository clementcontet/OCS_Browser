package com.example.ocs_browser.ui.search

import com.example.ocs_browser.models.SearchResults

class SearchItemViewModel(result: SearchResults.Content) {
    val title: String = result.title[0].value
    val subtitle: String = result.subtitle
    val imageurl: String? =
        if (result.imageurl != null) ("https://statics.ocs.fr" + result.imageurl) else null
    val fullscreenimageurl: String? =
        if (result.fullscreenimageurl != null) ("https://statics.ocs.fr" + result.fullscreenimageurl) else null
}