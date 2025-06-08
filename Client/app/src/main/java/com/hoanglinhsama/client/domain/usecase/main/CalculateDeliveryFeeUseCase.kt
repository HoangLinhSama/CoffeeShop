package com.hoanglinhsama.client.domain.usecase.main

import javax.inject.Inject

class CalculateDeliveryFeeUseCase @Inject constructor() {
    operator fun invoke(address: String?, isDelivery: Boolean): Float {
        val shippingFee = (if (!isDelivery) {
            0
        } else {
            if (address?.lowercase()?.contains("hồ chí minh") == true ||
                address?.lowercase()?.contains("ho chi minh") == true ||
                address?.lowercase()?.contains("hochiminh") == true ||
                address?.lowercase()?.contains("hcm") == true
            ) {
                15000f
            } else {
                30000f
            }

        }).toFloat()
        return shippingFee
    }
}