package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class CalculateSubtotalUseCaseTest {
    private val useCase = CalculateSubtotalUseCase()

    @Test
    fun subtotal_isNull_whenListIsNull() {
        val subtotal = useCase.invoke(null)
        assertNull(subtotal)
    }

    @Test
    fun subtotal_isZero_whenListIsEmpty() {
        val subtotal = useCase.invoke(emptyList())
        assertEquals(0f, subtotal)
    }

    @Test
    fun subtotal_isCorrect_whenListHasOneItem() {
        val list = listOf(
            DrinkOrder(
                _id = 1,
                _picture = "",
                _name = "Bạc xỉu",
                _size = "Nhỏ",
                _listTopping = null,
                _note = "Bỏ ít đá",
                _count = 1,
                _price = 59000f,
                _drinkCategory = "Cafe"
            )
        )
        val subtotal = useCase.invoke(list)
        assertEquals(59000f, subtotal)
    }

    @Test
    fun subtotal_isCorrect_whenListHasMultipleItems() {
        val list = listOf(
            DrinkOrder(1, "", "Bạc xỉu", "Nhỏ", null, "Bỏ ít đá", 1, 59000F, "Cafe"),
            DrinkOrder(2, "", "Phin Sữa Tươi Bánh Flan", "Nhỏ", null, null, 1, 49000f, "Cafe"),
            DrinkOrder(4, "", "Trà Xanh Espresso Marble", "Nhỏ", null, null, 1, 49000f, "Trà xanh")
        )

        val subtotal = useCase.invoke(list)
        assertEquals(157000f, subtotal)
    }

    @Test
    fun subtotal_isCorrect_whenPricesHaveDecimal() {
        val list = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 1, 19999.99f, "Cafe"),
            DrinkOrder(2, "", "Phin Sữa Tươi Bánh Flan", "Nhỏ", null, null, 1, 10000.0f, "Cafe")
        )
        val subtotal = useCase.invoke(list)
        subtotal?.let { assertEquals(29999.99f, it, 0.01f) }
    }
}