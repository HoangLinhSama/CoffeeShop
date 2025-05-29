package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.Drink
import javax.inject.Inject

class ReceiveUpdateDrinkOrderUseCase @Inject constructor() {
    operator fun invoke(listDrink: List<Drink>?, drink: Drink?): MutableList<Drink>? {
        val listUpdateDrinkOrder = listDrink?.toMutableList()
        if (listDrink?.isNotEmpty() == true) {
            val index = listUpdateDrinkOrder?.indexOfFirst {
                it.id == drink?.id
            }
            if (index == -1) {
                drink?.let {
                    listUpdateDrinkOrder.add(it)
                }
            }
        } else {
            drink?.let {
                listUpdateDrinkOrder?.add(it)
            }
        }
        return listUpdateDrinkOrder
    }
}