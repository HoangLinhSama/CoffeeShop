package com.hoanglinhsama.client.presentation.viewmodel.common

import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.DrinkOrder
import kotlinx.coroutines.flow.MutableStateFlow

object TempOrderHolder {
    private val _tempOrder = MutableStateFlow<UniqueResult<DrinkOrder>?>(null)
    val tempOrder = _tempOrder

    fun setTempOrder(resultTempOrder: UniqueResult<DrinkOrder>) {
        _tempOrder.value = resultTempOrder
    }
}