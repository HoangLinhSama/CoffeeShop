package com.hoanglinhsama.client.data.mapper

import com.hoanglinhsama.client.data.model.Drink
import com.hoanglinhsama.client.data.model.DrinkCategory
import com.hoanglinhsama.client.data.model.Voucher

fun Voucher.toVoucherDomain(): com.hoanglinhsama.client.domain.model.Voucher {
    return com.hoanglinhsama.client.domain.model.Voucher(
        this.startDate,
        this.expirationDate,
        this.name,
        this.conditions,
        this.categoryDrink,
        this.picture,
        this.freeShip
    )
}

fun DrinkCategory.toDrinkCategoryDomain(): com.hoanglinhsama.client.domain.model.DrinkCategory {
    return com.hoanglinhsama.client.domain.model.DrinkCategory(this.name)
}

fun Drink.toDrinkDomain(): com.hoanglinhsama.client.domain.model.Drink {
    return com.hoanglinhsama.client.domain.model.Drink(
        this.id,
        this.name,
        listToMap(this.priceSize, keyMapper = {
            it.toString()
        }, valueMapper = {
            it.toInt()
        }),
        this.picture,
        this.star,
        this.description,
        listToMap(this.toppingPrice, keyMapper = {
            it.toString()
        }, valueMapper = {
            it.toInt()
        }),
        this.countReview
    )
}

fun <K : Any, T : Any> listToMap(
    list: List<String>?,
    keyMapper: (String) -> K,
    valueMapper: (String) -> T,
): Map<K, T>? {
    return list?.map {
        val parts = it.split(":")
        keyMapper(parts[0]) to valueMapper(parts[1])
    }?.toMap()
}