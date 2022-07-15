package ru.donkids.mobile.ui.screens.main.pages.home.entity

import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent

data class HomePageState(
    val banners: List<Banner> = ArrayList(),
    val history: List<Recent> = ArrayList()
)
