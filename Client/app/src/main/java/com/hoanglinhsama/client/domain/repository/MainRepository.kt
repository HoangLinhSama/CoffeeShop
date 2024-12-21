package com.hoanglinhsama.client.domain.repository

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getPromotion(): Flow<PagingData<Voucher>>
    fun getDrinkCategory(): Flow<PagingData<DrinkCategory>>
    fun getDrink(): Flow<PagingData<Drink>>
}
