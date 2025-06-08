package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.presentation.viewmodel.common.UpdateDrinkOrderHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SendUpdateDrinkOrderUseCaseTest {
    private lateinit var useCase: SendUpdateDrinkOrderUseCase

    @Before
    fun setup() {
        useCase = SendUpdateDrinkOrderUseCase()
    }

    @Test
    fun shouldUpdateDrinkInUpdateDrinkOrderHolder() = runTest {
        val priceSize = mapOf<String, Int>("Nhỏ" to 49000, "Vừa" to 55000, "Lớn" to 65000)
        val toppingPrice = mapOf<String, Int>(
            "Shot Espresso" to 10000, "Trân châu trắng" to 10000, "Sốt Caramel" to 10000
        )
        val drink = Drink(
            1,
            "Phin Sữa Tươi Bánh Flan",
            priceSize,
            "",
            5F,
            "Tỉnh tức thì cùng cà phê Robusta pha phin đậm đà và bánh flan núng nính. Uống là tỉnh, ăn là dính, xứng đáng là highlight trong ngày của bạn.",
            toppingPrice,
            1,
            "Cafe"
        )
        useCase(drink)
        val result = UpdateDrinkOrderHolder.updateDrinkOrder.first()
        assertThat(result).isEqualTo(drink)
        assertThat(result?.name).isEqualTo("Phin Sữa Tươi Bánh Flan")
    }
}
