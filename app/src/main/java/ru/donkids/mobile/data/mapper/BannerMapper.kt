package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.BannerEntity
import ru.donkids.mobile.domain.model.Banner

fun BannerEntity.toBanner(): Banner {
    return Banner(
        pagePath = pagePath,
        imagePath = imagePath,
        productCode = productCode
    )
}

fun Banner.toBannerEntity(): BannerEntity {
    return BannerEntity(
        pagePath = pagePath,
        imagePath = imagePath,
        productCode = productCode
    )
}
