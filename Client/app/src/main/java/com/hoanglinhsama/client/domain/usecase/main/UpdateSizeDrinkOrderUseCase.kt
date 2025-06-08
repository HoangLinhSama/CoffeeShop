package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import javax.inject.Inject
import kotlin.collections.get

class UpdateSizeDrinkOrderUseCase @Inject constructor() {
    operator fun invoke(
        listDrinkOrderCurrent: List<DrinkOrder>?,
        indexUpdateOrderDrink: Int,
        size: String,
        drink: Drink,
    ): MutableList<DrinkOrder>? {
        val listDrinkOrder = listDrinkOrderCurrent?.toMutableList() ?: return null
        var updateDrinkOrder =
            listDrinkOrder[indexUpdateOrderDrink]
        val priceSizeOld = drink.priceSize?.get(updateDrinkOrder.size)!!
        val priceSizeNew = drink.priceSize[size]!!
        val newPrice =
            (priceSizeNew - priceSizeOld) * updateDrinkOrder.count + updateDrinkOrder.price
        updateDrinkOrder = updateDrinkOrder.copy(_size = size, _price = newPrice)
        updateDrinkOrder.let {
            listDrinkOrder.set(indexUpdateOrderDrink, it)
        }
        return listDrinkOrder
    }
}