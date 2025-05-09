package com.hoanglinhsama.client.domain.repository

import androidx.paging.PagingData
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getPromotion(): Flow<PagingData<Voucher>>
    fun getDrinkCategory(): Flow<PagingData<DrinkCategory>>
    fun getDrink(): Flow<PagingData<Drink>>
    fun getUser(phone: String): Flow<Result<User>>
    fun getPhone(): Flow<String>
    suspend fun updateStateLogIn()
    suspend fun logOut()
    fun getShop(): Flow<PagingData<Shop>>
}
