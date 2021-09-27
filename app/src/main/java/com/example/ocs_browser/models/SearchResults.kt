package com.example.ocs_browser.models

data class SearchResults(
    val contents: List<Content>
) {
    data class Content(
        val title: List<Title>,
        val subtitle: String,
        val imageurl: String?,
        val fullscreenimageurl: String?
    ) {
        data class Title(
            val value: String
        )
    }
}