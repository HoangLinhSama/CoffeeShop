package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.DrinkOrder
import org.junit.Before
import org.junit.Test

class UpdateCountDrinkOrderUseCaseTest {
    private lateinit var useCase: UpdateCountDrinkOrderUseCase

    private val drinkOrder = DrinkOrder(
        1, "", "Bạc xỉu", "Nhỏ", listOf(
            "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
        ), "Bỏ ít đá", 1, 59000F, "Cafe"
    )

    @Before
    fun setUp() {
        useCase = UpdateCountDrinkOrderUseCase()
    }

    @Test
    fun shouldUpdateQuantityAndPriceCorrectly() {
        val listDrinkOrder = listOf(drinkOrder)
        val listUpdateDrinkOrder = useCase(listDrinkOrder, 0, 3)

        assertThat(listUpdateDrinkOrder).isNotNull()
        val updatedDrinkOrder = listUpdateDrinkOrder!![0]
        val expectedPrice = (59000 / 1) * 3

        assertThat(updatedDrinkOrder.count).isEqualTo(3)
        assertThat(updatedDrinkOrder.price).isEqualTo(expectedPrice)
    }

    @Test
    fun shouldReturnNull_whenInputListIsNull() {
        val result = useCase(null, 0, 5)
        assertThat(result).isNull()
    }
}