package com.hoanglinhsama.client.domain.usecase.main

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateTotalPaymentUseCaseTest {
    private val useCase = CalculateTotalPaymentUseCase()

    @Test
    fun totalPayment_isCorrect_whenAllParamsValid() {
        val shippingFee = 15000f
        val subTotal = 30000f
        val discount = 5000f

        val total = useCase.invoke(shippingFee, subTotal, discount)
        total?.let { assertEquals(40000f, it, 0.001f) }
    }

    @Test
    fun totalPayment_isNull_whenShippingFeeIsNull() {
        val subTotal = 30000f
        val discount = 5000f

        val total = useCase.invoke(null, subTotal, discount)
        assertEquals(null, total)
    }

    @Test
    fun totalPayment_isNull_whenSubTotalIsNull() {
        val shippingFee = 15000f
        val discount = 5000f

        val total = useCase.invoke(shippingFee, null, discount)
        assertEquals(null, total)
    }

    @Test
    fun totalPayment_canBeNegative_whenDiscountIsHigh() {
        val shippingFee = 10000f
        val subTotal = 20000f
        val discount = 35000f

        val total = useCase.invoke(shippingFee, subTotal, discount)
        total?.let { assertEquals(-5000f, it, 0.001f) }
    }
}