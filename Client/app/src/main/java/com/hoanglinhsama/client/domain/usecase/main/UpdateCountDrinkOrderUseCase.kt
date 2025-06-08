package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import javax.inject.Inject

class UpdateCountDrinkOrderUseCase @Inject constructor() {
    operator fun invoke(
        listDrinkOrderCurrent: List<DrinkOrder>?,
        indexUpdateOrderDrink: Int, quantity: Int,
    ): MutableList<DrinkOrder>? {
        val listDrinkOrder = listDrinkOrderCurrent?.toMutableList() ?: return null
        var updateDrinkOrder = listDrinkOrder[indexUpdateOrderDrink]
        val price = (updateDrinkOrder.price / updateDrinkOrder.count) * quantity
        updateDrinkOrder = updateDrinkOrder.copy(_price = price, _count = quantity)
        updateDrinkOrder.let {
            listDrinkOrder.set(indexUpdateOrderDrink, it)
        }
        return listDrinkOrder
    }
}