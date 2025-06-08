package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UpdateUseVoucherUseCaseTest {
    private val useCase = UpdateUseVoucherUseCase()

    @Test
    fun shouldApplyPercentageDiscount_andFreeShipping() {
        val result = useCase(
            value = 0.2f,
            subTotal = 100000f,
            shippingFee = 20000f,
            freeShip = true
        )

        assertThat(result).isEqualTo(
            UpdateUseVoucherResult(
                discount = 20000f,
                shippingFee = 0f
            )
        )
    }
    @Test
    fun shouldApplyFixedDiscount_andNoFreeShipping() {
        val result = useCase(
            value = 15000f,
            subTotal = 100000f,
            shippingFee = 25000f,
            freeShip = false
        )

        assertThat(result).isEqualTo(
            UpdateUseVoucherResult(
                discount = 15000f,
                shippingFee = 25000f
            )
        )
    }
    @Test
    fun shouldApplyPercentageDiscount_andNormalShipping() {
        val result = useCase(
            value = 0.5f,
            subTotal = 80000f,
            shippingFee = 15000f,
            freeShip = false
        )

        assertThat(result).isEqualTo(
            UpdateUseVoucherResult(
                discount = 40000f,
                shippingFee = 15000f
            )
        )
    }
    @Test
    fun shouldApplyFixedDiscount_andFreeShipping() {
        val result = useCase(
            value = 10000f,
            subTotal = 50000f,
            shippingFee = 10000f,
            freeShip = true
        )

        assertThat(result).isEqualTo(
            UpdateUseVoucherResult(
                discount = 10000f,
                shippingFee = 0f
            )
        )
    }
}