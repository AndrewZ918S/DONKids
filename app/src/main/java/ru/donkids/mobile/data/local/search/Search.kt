package ru.donkids.mobile.data.local.search

import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.util.radixSortedBy
import ru.donkids.mobile.util.radixSortedByDescending

fun List<Product>.relevantSearch(query: String): List<Product> {
    return search(query)
        .sortedByCondition { it.titleMatch }
        .radixSortedBy { it.matchIndex }
        .sortedByCondition { it.exactMatch }
        .sortedByCondition { it.prefixMatch }
        .radixSortedByDescending { it.matches }
        .map { it.product }
}

fun <T> Iterable<T>.sortedByCondition(transform: (T) -> Boolean): List<T> {
    val sortedList = ArrayList<T>()

    sortedList.addAll(filter { transform(it) })
    sortedList.addAll(filter { !transform(it) })

    return sortedList
}

fun List<Product>.search(query: String): List<MatchInfo> {
    val words = query.trim().lowercase().split(Regex("\\s+"))

    val matches = ArrayList<MatchInfo>()

    forEach { product ->
        val fields = listOf(
            product.title,
            product.keywords,
            product.code,
            product.vendorCode,
            product.barcode.toString()
        ).map { it.trim().lowercase() }

        val matchInfo = MatchInfo(product)

        for (wordIndex in words.indices) {
            val word = words[wordIndex]

            var wordMatched = false
            for (fieldIndex in fields.indices) {
                val field = fields[fieldIndex]

                val fuzzyMatchInfo = field.fuzzySearch(word)
                val matchFactor = fuzzyMatchInfo.matchFactor
                val editDistance = fuzzyMatchInfo.distance
                val firstIndex = fuzzyMatchInfo.index

                if (firstIndex < 0 || editDistance > 2 || matchFactor < 0.75f) {
                    continue
                }

                if (matchFactor == 1.0f) {
                    matchInfo.exactMatch = true
                }

                matchInfo.matchIndex = firstIndex.coerceAtMost(matchInfo.matchIndex)

                if (!wordMatched) {
                    wordMatched = true
                    matchInfo.matches++
                }

                if (fieldIndex == 0) {
                    matchInfo.titleMatch = true
                }

                if (!matchInfo.prefixMatch) {
                    val prevIndex = (firstIndex - 1).coerceAtLeast(0)
                    val whitespaceBefore = field[prevIndex].isWhitespace()

                    if (prevIndex == firstIndex || whitespaceBefore) {
                        matchInfo.prefixMatch = true
                    }
                }
            }
        }

        if (matchInfo.matches > 0) {
            matches.add(matchInfo)
        }
    }

    return matches
}
