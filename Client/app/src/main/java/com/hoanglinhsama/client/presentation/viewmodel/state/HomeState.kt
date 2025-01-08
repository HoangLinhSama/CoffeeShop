package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import kotlinx.coroutines.flow.Flow

data class HomeState(
    private val _itemsVoucher: Flow<PagingData<Voucher>>? = null,
    private val _itemsDrinkCategory: Flow<PagingData<DrinkCategory>>? = null,
    private val _itemsDrink: Flow<PagingData<Drink>>? = null,
    private val _user: User? = null,
    private val _selectedDrinkCategory: Int = -1,
    private val _currentPromotionIndex: Int = 0,
) {
    val itemsVoucher = _itemsVoucher
    val itemsDrinkCategory = _itemsDrinkCategory
    val itemsDrink = _itemsDrink
    val user = _user
    val selectedDrinkCategory = _selectedDrinkCategory
    val currentPromotionIndex = _currentPromotionIndex

}