package ru.donkids.mobile.data.local.search

import ru.donkids.mobile.domain.model.Product

data class MatchInfo(
    val product: Product,
    var matches: Int = 0,
    var matchIndex: Int = Int.MAX_VALUE,
    var titleMatch: Boolean = false,
    var exactMatch: Boolean = false,
    var prefixMatch: Boolean = false
)
