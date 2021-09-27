package com.example.ocs_browser.ui.search

import com.example.ocs_browser.models.SearchResults

class SearchItemViewModel(result: SearchResults.Content) {
    val title: String = result.title[0].value
    val subtitle: String = result.subtitle
}