package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.ProductEntity
import ru.donkids.mobile.data.remote.dto.ProductDto
import ru.donkids.mobile.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        description = description,
        isCategory = isCategory,
        properties = properties,
        imageHash = imageHash,
        imageLink = imageLink,
        keywords = keywords,
        abbreviation = abbreviation,
        title = title,
        isAvailable = isAvailable,
        size = size,
        parentId = parentId,
        barcode = barcode,
        updateIndex = updateIndex
    )
}

fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        description = description,
        isCategory = isCategory,
        properties = properties,
        imageHash = imageHash,
        imageLink = imageLink,
        keywords = keywords,
        abbreviation = abbreviation,
        title = title,
        isAvailable = isAvailable,
        size = size,
        parentId = parentId,
        barcode = barcode,
        updateIndex = updateIndex
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        description = description,
        isCategory = isCategory,
        properties = properties,
        imageHash = imageHash,
        imageLink = imageLink,
        keywords = keywords,
        abbreviation = abbreviation,
        title = title,
        isAvailable = isAvailable,
        size = size,
        parentId = parentId,
        barcode = barcode,
        updateIndex = updateIndex
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        code = code,
        vendorCode = vendorCode,
        price = price,
        description = description,
        isCategory = isCategory,
        properties = properties,
        imageHash = imageHash,
        imageLink = imageLink,
        keywords = keywords,
        abbreviation = abbreviation,
        title = title,
        isAvailable = isAvailable,
        size = size,
        parentId = parentId,
        barcode = barcode,
        updateIndex = updateIndex
    )
}
