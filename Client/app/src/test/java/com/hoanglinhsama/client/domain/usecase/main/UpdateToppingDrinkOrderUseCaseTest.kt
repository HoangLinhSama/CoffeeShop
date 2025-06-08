package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkOrder
import org.junit.Test

class UpdateToppingDrinkOrderUseCaseTest {
    private val useCase = UpdateToppingDrinkOrderUseCase()
    val priceSize = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
    val toppingPrice =
        mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000, "Sốt Caramel" to 10000)
    private val drink = Drink(1, "Bạc xỉu", priceSize, "", 5F, "", toppingPrice, 1, "Cafe")

    @Test
    fun shouldAddTopping_andUpdatePriceCorrectly() {
        val currentDrinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 49000F, "Cafe"
        )

        val result = useCase(
            listOf(currentDrinkOrder), 0, true, 0, drink
        )

        val updatedDrinkOrder = result!![0]
        assertThat(updatedDrinkOrder.listTopping).containsExactly(
            "Shot Espresso",
            "Trân châu trắng",
            "Sốt Caramel"
        )
        assertThat(updatedDrinkOrder.price).isEqualTo(59000f)
    }

    @Test
    fun shouldRemoveTopping_andUpdatePriceCorrectly() {
        val currentDrinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 49000F, "Cafe"
        )

        val result = useCase(listDrinkOrderCurrent = listOf(currentDrinkOrder), 0, false, 1, drink)

        val updatedDrinkOrder = result!![0]
        assertThat(updatedDrinkOrder.listTopping).containsExactly("Sốt Caramel")

        assertThat(updatedDrinkOrder.price).isEqualTo(39000f)
    }

    @Test
    fun shouldReturnNull_ifListIsNull() {
        val result = useCase(
            listDrinkOrderCurrent = null,
            indexUpdateOrderDrink = 0,
            isSelect = true,
            index = 0,
            drink = drink
        )
        assertThat(result).isNull()
    }
}