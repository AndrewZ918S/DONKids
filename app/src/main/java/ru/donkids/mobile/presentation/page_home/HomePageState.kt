package ru.donkids.mobile.presentation.page_home

import ru.donkids.mobile.domain.model.Banner

data class HomePageState(
    val banners: List<Banner> = ArrayList()
)
