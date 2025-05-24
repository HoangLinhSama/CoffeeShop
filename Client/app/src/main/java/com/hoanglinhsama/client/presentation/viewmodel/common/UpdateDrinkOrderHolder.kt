package com.hoanglinhsama.client.presentation.viewmodel.common

import com.hoanglinhsama.client.domain.model.Drink
import kotlinx.coroutines.flow.MutableStateFlow

object UpdateDrinkOrderHolder {
    private val _updateDrinkOrder = MutableStateFlow<Drink?>(null)
    val updateDrinkOrder = _updateDrinkOrder

    fun setUpdateDrinkOrder(updateDrink: Drink) {
        _updateDrinkOrder.value = updateDrink
    }
}