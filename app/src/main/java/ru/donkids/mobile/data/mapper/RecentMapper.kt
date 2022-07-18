package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.RecentEntity
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.domain.model.Recent

fun RecentEntity.toRecent(): Recent {
    return Recent(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        imagePath = imagePath,
        abbreviation = abbreviation,
        isAvailable = isAvailable,
        timestamp = timestamp
    )
}

fun Recent.toRecentEntity(): RecentEntity {
    return RecentEntity(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        imagePath = imagePath,
        abbreviation = abbreviation,
        isAvailable = isAvailable,
        timestamp = timestamp
    )
}

fun Product.toRecent(): Recent {
    return Recent(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        imagePath = imagePath,
        abbreviation = abbreviation,
        isAvailable = isAvailable,
        timestamp = System.currentTimeMillis()
    )
}
