package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import org.junit.Test

class UpdateSizeDrinkOrderUseCaseTest {
    private val useCase = UpdateSizeDrinkOrderUseCase()

    @Test
    fun shouldUpdateSizeAndPriceCorrectly() {
        val priceSize = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
        val toppingPrice = mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000)
        val drink = Drink(1, "Bạc xỉu", priceSize, "", 5F, "", toppingPrice, 1, "Cafe")

        val currentDrinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )

        val currentListDrinkOrder = listOf(currentDrinkOrder)

        val updatedListDrinkOrder = useCase(
            currentListDrinkOrder, 0, "Vừa", drink
        )

        val updatedDrinkOrder = updatedListDrinkOrder!![0]
        assertThat(updatedDrinkOrder.size).isEqualTo("Vừa")

        assertThat(updatedDrinkOrder.price).isEqualTo(69000f)
    }

    @Test
    fun `invoke should return null when list is null`() {
        val priceSize = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
        val toppingPrice = mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000)
        val drink = Drink(1, "Bạc xỉu", priceSize, "", 5F, "", toppingPrice, 1, "Cafe")

        val result = useCase(
            listDrinkOrderCurrent = null,
            indexUpdateOrderDrink = 0,
            size = "Vừa",
            drink = drink
        )

        assertThat(result).isNull()
    }
}