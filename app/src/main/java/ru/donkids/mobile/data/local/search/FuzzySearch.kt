package ru.donkids.mobile.data.local.search

fun String.fuzzySearch(query: String): FuzzyMatchInfo {
    val first = lowercase()
    val second = query.lowercase()

    var bestMatch = 0
    var bestIndex = -1
    val skipLimit = query.length.coerceAtMost(length) / 2

    var index = 0
    while (index < length && bestMatch < query.length) {
        var match = 0
        var skip = 0

        var indexOffset = 0
        while (index + indexOffset < length && indexOffset < query.length && skip <= skipLimit) {
            if (first[index + indexOffset] == second[indexOffset]) {
                match++
            } else {
                skip++
            }

            indexOffset++
        }

        if (match > bestMatch) {
            bestMatch = match
            bestIndex = index
        }

        index++
    }

    return FuzzyMatchInfo(
        matchFactor = bestMatch.toFloat() / query.length,
        distance = query.length - bestMatch,
        index = bestIndex,
    )
}
