package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.Voucher
import javax.inject.Inject

class CheckVoucherAvailabilityUseCase @Inject constructor() {
    operator fun invoke(
        voucher: Voucher,
        listDrinkOrder: List<DrinkOrder>?,
        isDelivery: Boolean,
        subTotal: Float,
    ): Boolean {
        val typeOrder = if (isDelivery) "delivery" else "takeaway"
        val quantity = listDrinkOrder?.sumOf {
            it.count
        }
        val listDrinkCategory =
            listDrinkOrder?.map { it.drinkCategory }?.distinct()
        val drinkCategoryCondition = listDrinkCategory?.any { drinkCategory ->
            drinkCategory in voucher.categoryDrink
        }
        return if (voucher.conditions.toString().length < 4) {
            val quantityCondition = voucher.conditions
            voucher.type == typeOrder &&
                    quantity!! >= quantityCondition &&
                    drinkCategoryCondition == true
        } else {
            val priceCondition = voucher.conditions
            voucher.type == typeOrder &&
                    subTotal >= priceCondition &&
                    drinkCategoryCondition == true
        }
    }
}