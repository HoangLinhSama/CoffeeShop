package com.hoanglinhsama.client.domain.repository

import androidx.paging.PagingData
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.OrderStatus
import com.hoanglinhsama.client.domain.model.OrderZaloPay
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getPromotion(phone: String): Flow<PagingData<Voucher>>
    fun getDrinkCategory(): Flow<PagingData<DrinkCategory>>
    fun getDrink(): Flow<PagingData<Drink>>
    fun getUser(phone: String): Flow<Result<User>>
    fun getPhone(): Flow<String>
    suspend fun updateStateLogIn()
    suspend fun logOut()
    fun getShop(): Flow<PagingData<Shop>>
    fun getRequiredBean(phone: String): Flow<Result<Int>>
    fun insertOrder(
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
        listDrinkOrder: List<com.hoanglinhsama.client.data.model.DrinkOrder>,
    ): Flow<Result<Int>>

    fun getOrderStatus(orderId: Int): Flow<Result<OrderStatus>>
    fun createOrderZaloPay(pay: OrderZaloPay)
}
