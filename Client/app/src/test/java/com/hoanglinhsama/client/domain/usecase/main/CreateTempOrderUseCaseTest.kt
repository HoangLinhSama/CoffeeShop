package com.hoanglinhsama.client.domain.usecase.main

import app.cash.turbine.test
import com.hoanglinhsama.client.data.model.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreateTempOrderUseCaseTest {
    private val useCase = CreateTempOrderUseCase()

    @Test
    fun shouldEmitLoadingAndSuccess_whenInvokeWithValidParams() = runTest {
        val id = 1
        val picture = ""
        val name = "Bạc xỉu"
        val size = "Nhỏ"
        val listTopping = listOf("Shot Espresso", "Trân châu trắng")
        val noteOrder = "Ít đường"
        val countDrink = 1
        val totalPrice = 49f
        val drinkCategory = "Cafe"

        val flow = useCase.invoke(id, picture, name, size, listTopping, noteOrder, countDrink, totalPrice, drinkCategory)

        flow.test {
            val loadingItem = awaitItem()
            assertTrue(loadingItem.result is Result.Loading)

            val successItem = awaitItem()
            assertTrue(successItem.result is Result.Success<*>)

            val order = (successItem.result as Result.Success).data
            assertEquals(id, order.id)
            assertEquals(picture, order.picture)
            assertEquals(name, order.name)
            assertEquals(size, order.size)
            assertEquals(listTopping, order.listTopping)
            assertEquals(noteOrder, order.note)
            assertEquals(countDrink, order.count)
            assertEquals(totalPrice, order.price)
            assertEquals(drinkCategory, order.drinkCategory)

            cancelAndIgnoreRemainingEvents()
        }
    }
}