package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.mapper.toDataDrinkOrder
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertOrderUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(
        userId: Int,
        name: String?,
        phone: String?,
        address: String?,
        dateTime: String,
        quantityBeanUse: Int?,
        paymentMethod: String,
        shopId: Int?,
        isDelivery: Boolean,
        deliveryFee: Float,
        subTotal: Float,
        totalPrice: Float,
        voucherId: Int?,
        paymentBillId: String?,
        listDrinkOrder: List<DrinkOrder>,
    ): Flow<Result<Int>> {
        val listDrinkOrderData = listDrinkOrder.map {
            it.toDataDrinkOrder()
        }
        return mainRepository.insertOrder(
            userId,
            name,
            phone,
            address,
            dateTime,
            quantityBeanUse,
            paymentMethod,
            shopId,
            isDelivery,
            deliveryFee,
            subTotal,
            totalPrice,
            voucherId,
            paymentBillId,
            listDrinkOrderData
        )
    }
}