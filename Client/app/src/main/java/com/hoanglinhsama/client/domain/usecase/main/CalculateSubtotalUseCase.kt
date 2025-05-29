package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import javax.inject.Inject

class CalculateSubtotalUseCase @Inject constructor() {
    operator fun invoke(listDrinkOrder: List<DrinkOrder>?): Float? {
        val subTotal = listDrinkOrder?.sumOf {
            it.price.toInt()
        }?.toFloat()
        return subTotal
    }
}