package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.BannerEntity
import ru.donkids.mobile.domain.model.Banner

fun BannerEntity.toBanner(): Banner {
    return Banner(
        page = page,
        image = image,
        code = code
    )
}

fun Banner.toBannerEntity(): BannerEntity {
    return BannerEntity(
        page = page,
        image = image,
        code = code
    )
}
