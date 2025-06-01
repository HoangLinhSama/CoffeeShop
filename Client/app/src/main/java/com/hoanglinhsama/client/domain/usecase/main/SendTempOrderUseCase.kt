package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import javax.inject.Inject

class SendTempOrderUseCase @Inject constructor() {
    operator fun invoke(resultTempOrder: UniqueResult<DrinkOrder>) {
        TempOrderHolder.setTempOrder(resultTempOrder)
    }
}