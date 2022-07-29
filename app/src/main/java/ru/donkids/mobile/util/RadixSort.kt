package ru.donkids.mobile.util

import kotlin.math.absoluteValue

fun Iterable<Int>.radixSorted(): List<Int> {
    return radixSort().toMutableList()
}

fun Iterable<Int>.radixSortedDescending(): List<Int> {
    return radixSort().reversed()
}

fun <T> Iterable<T>.radixSortedBy(transform: (T) -> Int): List<T> {
    return groupBy(transform).radixSort()
}

fun <T> Iterable<T>.radixSortedByDescending(transform: (T) -> Int): List<T> {
    return groupBy(transform).radixSortDescending()
}

private fun <T> Map<Int, List<T>>.radixSort(): List<T> {
    val negativeKeys = ArrayList<Int>()
    val positiveKeys = ArrayList<Int>()

    keys.forEach { key ->
        if (key < 0) {
            negativeKeys.add(-key)
        } else {
            positiveKeys.add(key)
        }
    }

    val sortedValues = ArrayList<T>()
    negativeKeys.radixSort().forEach { key ->
        sortedValues.addAll(0, get(-key)!!)
    }
    positiveKeys.radixSort().forEach { key ->
        sortedValues.addAll(get(key)!!)
    }

    return sortedValues
}

private fun <T> Map<Int, List<T>>.radixSortDescending(): List<T> {
    val negativeKeys = ArrayList<Int>()
    val positiveKeys = ArrayList<Int>()

    keys.forEach { key ->
        if (key < 0) {
            negativeKeys.add(-key)
        } else {
            positiveKeys.add(key)
        }
    }

    val sortedValues = ArrayList<T>()
    negativeKeys.radixSort().forEach { key ->
        sortedValues.addAll(get(-key)!!)
    }
    positiveKeys.radixSort().forEach { key ->
        sortedValues.addAll(0, get(key)!!)
    }

    return sortedValues
}

private fun Iterable<Int>.radixSort(): IntArray {
    var array = map { it.absoluteValue }.toIntArray()

    var currentPlace = 1
    val maxValue = array.maxOrNull() ?: 0

    while (maxValue / currentPlace > 0) {
        array = array.sortByPlace(currentPlace)
        currentPlace *= 10
    }

    return array
}

private fun IntArray.sortByPlace(
    place: Int
): IntArray {
    val indexes = getDigitIndexes(place)

    val sorted = IntArray(size)
    for (i in indices.reversed()) {
        val int = get(i)
        val digit = int[place]
        val index = --indexes[digit]

        sorted[index] = int
    }

    return sorted
}

private fun IntArray.getDigitIndexes(place: Int): IntArray {
    val indexes = IntArray(10)

    forEach { int ->
        indexes[int[place]]++
    }

    for (i in 1..9) {
        indexes[i] += indexes[i - 1]
    }

    return indexes
}

private operator fun Int.get(place: Int): Int {
    return div(place) % 10
}
