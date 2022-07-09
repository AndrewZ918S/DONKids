package ru.donkids.mobile.presentation.page_home

import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent

sealed class HomePageEvent {
    data class OpenBanner(
        val banner: Banner
    ) : HomePageEvent()

    data class OpenRecent(
        val recent: Recent
    ) : HomePageEvent()

    object Refresh : HomePageEvent()
}