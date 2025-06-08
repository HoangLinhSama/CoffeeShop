package com.hoanglinhsama.client.domain.usecase.main

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateDeliveryFeeUseCaseTest {
    private val useCase = CalculateDeliveryFeeUseCase()

    @Test
    fun fee_isZero_whenIsDeliveryIsFalse() {
        val fee = useCase.invoke("any address", false)
        assertEquals(0f, fee)
    }

    @Test
    fun fee_is15000_whenIsDeliveryIsTrue_andAddressContainsHoChiMinhVariants() {
        val addresses = listOf(
            "123 đường Hồ Chí Minh",
            "Số 5, ho chi minh city",
            "Ở hochiminh quận 1",
            "Tôi ở HCM"
        )

        addresses.forEach { address ->
            val fee = useCase.invoke(address, true)
            assertEquals(15000f, fee)
        }
    }

    @Test
    fun fee_is30000_whenIsDeliveryIsTrue_andAddressDoesNotContainHoChiMinh() {
        val fee = useCase.invoke("Hà Nội", true)
        assertEquals(30000f, fee)
    }

    @Test
    fun fee_is30000_whenIsDeliveryIsTrue_andAddressIsNull() {
        val fee = useCase.invoke(null, true)
        assertEquals(30000f, fee)
    }
}