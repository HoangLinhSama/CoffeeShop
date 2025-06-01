package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import javax.inject.Inject

class UpdateToppingDrinkOrderUseCase @Inject constructor() {
    operator fun invoke(
        listDrinkOrderCurrent: List<DrinkOrder>?,
        indexUpdateOrderDrink: Int,
        isSelect: Boolean,
        index: Int,
        drink: Drink,
    ): MutableList<DrinkOrder>? {
        val listDrinkOrder = listDrinkOrderCurrent?.toMutableList()
        var updateDrinkOrder = listDrinkOrder?.get(indexUpdateOrderDrink)
        val listUpdateTopping = updateDrinkOrder?.listTopping?.toMutableList()
        val listTopping = drink.toppingPrice?.keys?.toList()
        val topping = listTopping?.get(index)
        val totalToppingPriceOld = updateDrinkOrder?.listTopping?.sumOf {
            drink.toppingPrice?.get(it) ?: 0
        } ?: 0
        if (isSelect) {
            listUpdateTopping?.add(topping.toString())
        } else {
            listUpdateTopping?.remove(topping.toString())
        }
        updateDrinkOrder = updateDrinkOrder?.copy(_listTopping = listUpdateTopping)
        val totalToppingPriceNew = updateDrinkOrder?.listTopping?.sumOf {
            drink.toppingPrice?.get(it) ?: 0
        } ?: 0
        val price = (totalToppingPriceNew.minus(totalToppingPriceOld)).times(
            updateDrinkOrder?.count ?: 1
        ).plus(updateDrinkOrder!!.price)
        updateDrinkOrder = updateDrinkOrder.copy(_price = price)
        updateDrinkOrder.let {
            listDrinkOrder?.set(indexUpdateOrderDrink, it)
        }
        return listDrinkOrder
    }
}