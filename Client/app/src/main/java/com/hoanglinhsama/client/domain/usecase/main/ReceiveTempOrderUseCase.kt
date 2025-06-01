package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import javax.inject.Inject

class ReceiveTempOrderUseCase @Inject constructor() {
    operator fun invoke(
        currentOrders: List<DrinkOrder>?,
        data: DrinkOrder,
    ): MutableList<DrinkOrder>? {
        val listDrinkOrder = currentOrders?.toMutableList()
        if (currentOrders?.isNotEmpty() == true) {
            val index = listDrinkOrder?.indexOfFirst { drinkOrder ->
                drinkOrder.id == data.id && drinkOrder.size == data.size && drinkOrder.listTopping == data.listTopping && drinkOrder.note == data.note
            }
            index?.let { index ->
                if (index != -1) {
                    val drinkOrder = listDrinkOrder[index]
                    listDrinkOrder[index] = drinkOrder.copy(
                        _count = drinkOrder.count + data.count,
                        _price = drinkOrder.price + data.price
                    )
                } else {
                    listDrinkOrder.add(data)
                }
            }
        } else {
            listDrinkOrder?.add(data)
        }
        return listDrinkOrder
    }
}
