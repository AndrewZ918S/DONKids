package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.BannerEntity
import ru.donkids.mobile.domain.model.Banner

fun BannerEntity.toBanner(): Banner {
    return Banner(
        pageLink = pageLink,
        imageLink = imageLink,
        vendorCode = vendorCode
    )
}

fun Banner.toBannerEntity(): BannerEntity {
    return BannerEntity(
        pageLink = pageLink,
        imageLink = imageLink,
        vendorCode = vendorCode
    )
}
