package ru.donkids.mobile.data.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.annotations.ImageHash
import ru.donkids.mobile.data.remote.annotations.ImageLink

internal class ImageAdapter {
    @FromJson
    @ImageHash
    fun anyToHash(any: Any): String {
        return any.toString()
    }

    @ToJson
    fun hashToStr(@ImageHash str: String): String {
        return str
    }

    @FromJson
    @ImageLink
    fun strToLink(str: String): String {
        return str.removePrefix(DonKidsApi.SITE_URL)
    }

    @ToJson
    fun linkToStr(@ImageLink str: String): String {
        return str
    }
}
