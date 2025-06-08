package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.domain.model.Drink
import org.junit.Before
import org.junit.Test

class ReceiveUpdateDrinkOrderUseCaseTest {
    private lateinit var receiveUpdateDrinkOrderUseCase: ReceiveUpdateDrinkOrderUseCase

    val priceSize = mapOf("Nhỏ" to 29000, "Vừa" to 39000, "Lớn" to 45000)
    val toppingPrice = mapOf("Shot Espresso" to 10000, "Trân châu trắng" to 10000)
    private val drink = Drink(1, "Bạc Sỉu", priceSize, "", 5F, "", toppingPrice, 1, "Cafe")

    @Before
    fun setup() {
        receiveUpdateDrinkOrderUseCase = ReceiveUpdateDrinkOrderUseCase()
    }

    @Test
    fun givenNullList_shouldReturnListWithDrinkAdded() {
        val result = receiveUpdateDrinkOrderUseCase.invoke(null, drink)

        assertThat(result).isNotNull()
        assertThat(result).hasSize(1)
        assertThat(result!![0]).isEqualTo(drink)
    }

    @Test
    fun givenEmptyList_shouldReturnListWithDrinkAdded() {
        val result = receiveUpdateDrinkOrderUseCase.invoke(emptyList(), drink)

        assertThat(result).isNotNull()
        assertThat(result).hasSize(1)
        assertThat(result!![0]).isEqualTo(drink)
    }

    @Test
    fun givenListWithSameDrinkId_shouldReturnSameListWithoutAdding() {
        val list = listOf(drink)
        val result = receiveUpdateDrinkOrderUseCase.invoke(list, drink)

        assertThat(result).isNotNull()
        assertThat(result).hasSize(1)
        assertThat(result!![0]).isEqualTo(drink)
    }

    @Test
    fun givenListWithoutDrinkId_shouldAddNewDrink() {
        val otherDrink = drink.copy(_id = 2)
        val list = listOf(drink)
        val result = receiveUpdateDrinkOrderUseCase.invoke(list, otherDrink)

        assertThat(result).isNotNull()
        assertThat(result).hasSize(2)
        assertThat(result).containsExactly(drink, otherDrink)
    }

    @Test
    fun givenNullDrink_shouldReturnOriginalListUnchanged() {
        val list = listOf(drink)
        val result = receiveUpdateDrinkOrderUseCase.invoke(list, null)

        assertThat(result).isNotNull()
        assertThat(result).hasSize(1)
        assertThat(result!![0]).isEqualTo(drink)
    }
}