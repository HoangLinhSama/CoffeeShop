package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class ReceiveTempOrderUseCaseTest {
    private lateinit var useCase: ReceiveTempOrderUseCase

    @Before
    fun setUp() {
        useCase = ReceiveTempOrderUseCase()
    }

    @Test
    fun givenSameDrinkExists_shouldIncreaseCountAndPrice() {
        val existing = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
        val newOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 2, 118000F, "Cafe"
        )

        val result = useCase.invoke(listOf(existing), newOrder)

        assertEquals(1, result!!.size)
        assertEquals(3, result[0].count)
        assertEquals(177000F, result[0].price)
    }

    @Test
    fun givenDifferentToppingOrNote_shouldAddAsNewDrink() {
        val existing =DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
        val newOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso"
            ), "Bỏ ít đường", 1, 39000F, "Cafe"
        )

        val result = useCase.invoke(listOf(existing), newOrder)

        assertEquals(2, result!!.size)
        assertTrue(result.contains(existing))
        assertTrue(result.contains(newOrder))
    }
}