package com.hoanglinhsama.client.domain.model.mapper

import com.hoanglinhsama.client.domain.model.DrinkOrder

fun DrinkOrder.toDataDrinkOrder(): com.hoanglinhsama.client.data.model.DrinkOrder {
    return com.hoanglinhsama.client.data.model.DrinkOrder(
        this.id,
        this.picture,
        this.name,
        this.size,
        this.listTopping,
        this.note,
        this.count,
        this.price,
        this.drinkCategory
    )
}