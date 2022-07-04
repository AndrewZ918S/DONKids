package ru.donkids.mobile.data.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ru.donkids.mobile.data.remote.annotations.ListObject
import ru.donkids.mobile.data.remote.dto.ProductDto

internal class ListAdapter {
    @FromJson
    @ListObject
    fun mapToList(map: Map<String, ProductDto>): List<ProductDto> {
        return map.values.toList()
    }

    @ToJson
    fun listToMap(@ListObject list: List<ProductDto>): Map<String, ProductDto> {
        val map = HashMap<String, ProductDto>()
        list.map { product ->
            map.put(product.id.toString(), product)
        }
        return map
    }
}
