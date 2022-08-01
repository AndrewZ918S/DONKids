package ru.donkids.mobile.ui.screens.search.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchScreenResult(
    val destinationId: Int? = null,
    val query: String? = null
): Parcelable
