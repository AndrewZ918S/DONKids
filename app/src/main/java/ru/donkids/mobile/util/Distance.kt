package ru.donkids.mobile.util

import kotlin.math.floor

fun String.jaroDistance(other: String): Float {
    val first = lowercase()
    val second = other.lowercase()

    if (first.isEmpty() || second.isEmpty()) {
        return 0f
    } else if (first == second) {
        return 1f
    }

    val maxDistance = floor(first.length.coerceAtLeast(second.length) / 2f).toInt() - 1

    var matches = 0f

    val firstHash = BooleanArray(first.length)
    val secondHash = BooleanArray(second.length)

    for (i in first.indices) {
        val start = (i - maxDistance).coerceAtLeast(
            minimumValue = 0
        )
        val end = second.length.coerceAtMost(
            maximumValue = i + maxDistance + 1
        )
        for (j in start until end) {
            if (first[i] == second[j] && !secondHash[j]) {
                firstHash[i] = true
                secondHash[j] = true
                matches++
                break
            }
        }
    }

    if (matches == 0f) {
        return 0f
    }

    var transpositions = 0f

    for (i in first.indices) {
        if (firstHash[i]) {
            var index = 0

            while (!secondHash[index]) {
                index++
            }

            if (get(i) != second[index]) {
                transpositions++
            }

            ++index
        }
    }
    transpositions /= 2f

    return (matches / first.length.toFloat() + matches / second.length.toFloat() + (matches - transpositions) / matches) / 3f
}

fun String.jaroWinkler(other: String): Float {
    val first = lowercase()
    val second = other.lowercase()

    var jaroDistance = first.jaroDistance(second)

    if (jaroDistance > 0.7f) {
        var prefix = 0
        for (i in 0 until first.length.coerceAtMost(second.length)) {
            if (first[i] == second[i] && prefix < 4) {
                prefix++
            } else {
                break
            }
        }

        jaroDistance += 0.1f * prefix * (1 - jaroDistance)
    }
    return jaroDistance
}
