package ru.donkids.mobile.data.local.search

data class FuzzyMatchInfo(
    val matchFactor: Float,
    val distance: Int,
    val index: Int
)
