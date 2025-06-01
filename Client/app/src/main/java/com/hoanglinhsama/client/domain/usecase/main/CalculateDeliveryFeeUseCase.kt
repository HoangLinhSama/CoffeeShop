package com.hoanglinhsama.client.domain.usecase.main

import javax.inject.Inject

class CalculateDeliveryFeeUseCase @Inject constructor() {
    operator fun invoke(address: String?, isDelivery: Boolean): Float {
        val shippingFee = (if (!isDelivery) {
            0
        } else {
            if (address?.contains("hồ chí minh") == true || address?.contains("ho chi minh") == true || address?.contains(
                    "hochiminh"
                ) == true || address?.contains("hcm") == true
            ) {
                15000
            } else {
                30000
            }
        }).toFloat()
        return shippingFee
    }
}