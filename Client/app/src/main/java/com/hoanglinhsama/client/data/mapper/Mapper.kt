package com.hoanglinhsama.client.data.mapper

import com.hoanglinhsama.client.data.model.Drink
import com.hoanglinhsama.client.data.model.DrinkCategory
import com.hoanglinhsama.client.data.model.Onboarding
import com.hoanglinhsama.client.data.model.Policies
import com.hoanglinhsama.client.data.model.Shop
import com.hoanglinhsama.client.data.model.User
import com.hoanglinhsama.client.data.model.Voucher

fun Voucher.toVoucherDomain(): com.hoanglinhsama.client.domain.model.Voucher {
    return com.hoanglinhsama.client.domain.model.Voucher(
        this.id,
        this.code,
        this.startDate,
        this.expirationDate,
        this.name,
        this.description,
        this.value,
        this.type,
        this.freeShip,
        this.conditions,
        this.categoryDrink,
        this.picture,
        this.qrCode
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
        this.countReview,
        this.drinkCategory
    )
}

fun <K, T> listToMap(
    list: List<String>?,
    keyMapper: (String) -> K,
    valueMapper: (String) -> T,
): Map<K, T>? {
    return list?.associate {
        val parts = it.split(":")
        keyMapper(parts[0]) to valueMapper(parts[1])
    }
}

fun Policies.toPolicyDomain(): com.hoanglinhsama.client.domain.model.Policies {
    return com.hoanglinhsama.client.domain.model.Policies(this.title, this.content)
}

fun User.toUserDomain(): com.hoanglinhsama.client.domain.model.User {
    return com.hoanglinhsama.client.domain.model.User(
        this.id,
        this.firstName,
        this.lastName,
        this.phone,
        this.address,
        this.image,
        this.memberShip,
        this.currentBean,
        this.collectedBean
    )
}

fun Onboarding.toOnboardingDomain(): com.hoanglinhsama.client.domain.model.Onboarding {
    return com.hoanglinhsama.client.domain.model.Onboarding(
        this.image,
        this.title,
        this.description
    )
}

fun Shop.toShopDomain(): com.hoanglinhsama.client.domain.model.Shop {
    return com.hoanglinhsama.client.domain.model.Shop(
        this.id,
        this.name,
        this.picture,
        this.address,
        this.phone,
        this.operatingHour
    )
}