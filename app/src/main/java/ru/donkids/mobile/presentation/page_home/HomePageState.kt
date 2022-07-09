package ru.donkids.mobile.presentation.page_home

import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent

data class HomePageState(
    val banners: List<Banner> = ArrayList(),
    val history: List<Recent> = ArrayList()
)
