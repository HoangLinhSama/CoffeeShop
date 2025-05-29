package com.hoanglinhsama.client.domain.usecase.main

import javax.inject.Inject

class UpdateUseVoucherUseCase @Inject constructor() {
    operator fun invoke(
        value: Float,
        subTotal: Float?,
        shippingFee: Float?,
        freeShip: Boolean,
    ): UpdateUseVoucherResult {
        val discount = if (value < 1) {
            subTotal?.times(value)
        } else {
            value
        }
        val shippingFee = if (freeShip == true) {
            0F
        } else {
            shippingFee
        }
        return UpdateUseVoucherResult(discount!!, shippingFee!!)
    }
}

data class UpdateUseVoucherResult(
    val discount: Float,
    val shippingFee: Float,
)