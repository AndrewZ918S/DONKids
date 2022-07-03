package ru.donkids.mobile.data.mapper

import ru.donkids.mobile.data.local.entities.ProductEntity
import ru.donkids.mobile.data.remote.dto.ProductDto
import ru.donkids.mobile.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id,
        code,
        vendorCode,
        price,
        description,
        isCategory,
        properties,
        imagePath,
        imageHash,
        imageLink,
        keywords,
        abbreviation,
        title,
        isAvailable,
        size,
        parentId,
        barcode,
        updateIndex
    )
}

fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id,
        code,
        vendorCode,
        price,
        description,
        isCategory,
        properties,
        imagePath,
        imageHash,
        imageLink,
        keywords,
        abbreviation,
        title,
        isAvailable,
        size,
        parentId,
        barcode,
        updateIndex
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id,
        code,
        vendorCode,
        price,
        description,
        isCategory,
        properties,
        imagePath,
        imageHash,
        imageLink,
        keywords,
        abbreviation,
        title,
        isAvailable,
        size,
        parentId,
        barcode,
        updateIndex
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id,
        code,
        vendorCode,
        price,
        description,
        isCategory,
        properties,
        imagePath,
        imageHash,
        imageLink,
        keywords,
        abbreviation,
        title,
        isAvailable,
        size,
        parentId,
        barcode,
        updateIndex
    )
}
