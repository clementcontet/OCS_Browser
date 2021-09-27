package com.example.ocs_browser.ui.search

import com.example.ocs_browser.models.SearchItem

class SearchItemViewModel(searchItem: SearchItem) {
    val title: String = searchItem.title[0].value
    val subtitle: String = searchItem.subtitle
    val imageurl: String? =
        if (searchItem.imageurl != null) ("https://statics.ocs.fr" + searchItem.imageurl) else null
}