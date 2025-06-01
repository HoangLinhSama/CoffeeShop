package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.presentation.viewmodel.common.UpdateDrinkOrderHolder
import javax.inject.Inject

class SendUpdateDrinkOrderUseCase @Inject constructor() {
    operator fun invoke(drink: Drink) {
        UpdateDrinkOrderHolder.setUpdateDrinkOrder(drink)
    }
}