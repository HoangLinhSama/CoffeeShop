package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.presentation.viewmodel.common.TempOrderHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SendTempOrderUseCaseTest {
    private lateinit var useCase: SendTempOrderUseCase

    @Before
    fun setup() {
        useCase = SendTempOrderUseCase()
    }

    @Test
    fun shouldSetTempOrderInTempOrderHolder() = runTest {
        val drinkOrder = DrinkOrder(
            1, "", "Bạc xỉu", "Nhỏ", listOf(
                "Shot Espresso", "Trân châu trắng", "Sốt Caramel"
            ), "Bỏ ít đá", 1, 59000F, "Cafe"
        )
        val uniqueResult = UniqueResult<DrinkOrder>(result = Result.Success(drinkOrder))

        useCase(uniqueResult)

        val result = TempOrderHolder.tempOrder.first()
        assertThat(result).isEqualTo(uniqueResult)
        assertThat(result?.result).isInstanceOf(Result.Success::class.java)
        assertThat((result?.result as Result.Success).data).isEqualTo(drinkOrder)
    }
    @Test
    fun shouldSetLoadingResult() = runTest {
        val loadingResult = UniqueResult<DrinkOrder>(result = Result.Loading)
        useCase(loadingResult)
        val result = TempOrderHolder.tempOrder.first()
        assertThat(result?.result).isInstanceOf(Result.Loading::class.java)
    }

    @Test
    fun shouldSetErrorResult() = runTest {
        val error = RuntimeException("test error")
        val errorResult = UniqueResult<DrinkOrder>(result = Result.Error(error))
        useCase(errorResult)
        val result = TempOrderHolder.tempOrder.first()
        assertThat(result?.result).isInstanceOf(Result.Error::class.java)
        assertThat((result?.result as Result.Error).exception.message).isEqualTo("test error")
    }
}