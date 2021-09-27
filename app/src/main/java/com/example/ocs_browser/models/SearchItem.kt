package com.example.ocs_browser.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItem(
    val title: List<Title>,
    val subtitle: String,
    val imageurl: String?,
    val fullscreenimageurl: String?,
    val detaillink: String?,
    val pitch: String?,
    val seasons: List<Season>?
) : Parcelable {
    @Parcelize
    data class Title(
        val value: String
    ) : Parcelable

    @Parcelize
    data class Season(
        val pitch: String?
    ) : Parcelable
}
