package com.hoanglinhsama.client.domain.usecase.main

import javax.inject.Inject

class CalculateTotalPaymentUseCase @Inject constructor() {
    operator fun invoke(shippingFee: Float?, subTotal: Float?, disCount: Float): Float? {
        val totalPayment = shippingFee?.let {
            subTotal?.plus(it)?.minus(disCount)
        }
        return totalPayment
    }
}