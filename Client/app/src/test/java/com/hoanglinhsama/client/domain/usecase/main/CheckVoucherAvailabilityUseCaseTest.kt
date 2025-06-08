package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.Voucher
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CheckVoucherAvailabilityUseCaseTest {
    private val useCase = CheckVoucherAvailabilityUseCase()

    private fun createVoucher(
        type: String,
        conditions: Int,
        categoryDrink: List<String>,
        freeShip: Boolean = false,
    ): Voucher {
        return Voucher(
            _id = 1,
            _code = "RONRANG20",
            _startDate = "2025-01-01",
            _expirationDate = "2025-12-31",
            _name = "Giảm 20K Đơn 60K",
            _description = "",
            _value = 20000f,
            _type = type,
            _freeShip = freeShip,
            _conditions = conditions,
            _categoryDrink = categoryDrink,
            _picture = "",
            _qrCode = ""
        )
    }

    @Test
    fun voucher_isAvailable_whenTypeMatchesQuantity_andCategoryConditionsMet() {
        val voucher = createVoucher(
            type = "delivery",
            conditions = 3,
            categoryDrink = listOf("Cafe", "Trà xanh")
        )

        val listDrinkOrder = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 2, 10000f, "Cafe"),
            DrinkOrder(2, "", "Trà Xanh Espresso Marble", "Nhỏ", null, null, 2, 15000f, "Trà xanh")
        )

        val result = useCase.invoke(voucher, listDrinkOrder, isDelivery = true, subTotal = 70000f)
        assertTrue(result)
    }

    @Test
    fun voucher_isNotAvailable_whenTypeDoesNotMatch() {
        val voucher = createVoucher(
            type = "takeaway",
            conditions = 2,
            categoryDrink = listOf("Cafe")
        )

        val listDrinkOrder = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 2, 10000f, "Cafe")
        )

        val result = useCase.invoke(voucher, listDrinkOrder, isDelivery = true, subTotal = 20000f)
        assertFalse(result)
    }

    @Test
    fun voucher_isAvailable_whenTypeMatches_andSubtotalConditionMet() {
        val voucher = createVoucher(
            type = "takeaway",
            conditions = 30000,
            categoryDrink = listOf("Cafe")
        )

        val listDrinkOrder = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 2, 35000f, "Cafe")
        )

        val result = useCase.invoke(voucher, listDrinkOrder, isDelivery = false, subTotal = 35000f)
        assertTrue(result)
    }

    @Test
    fun voucher_isNotAvailable_whenDrinkCategoryConditionFails() {
        val voucher = createVoucher(
            type = "delivery",
            conditions = 1,
            categoryDrink = listOf("Trà xanh")
        )

        val listDrinkOrder = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 2, 35000f, "Cafe")
        )

        val result = useCase.invoke(voucher, listDrinkOrder, isDelivery = true, subTotal = 15000f)
        assertFalse(result)
    }

    @Test
    fun voucher_isNotAvailable_whenQuantityIsLessThanCondition() {
        val voucher = createVoucher(
            type = "delivery",
            conditions = 3,
            categoryDrink = listOf("Cafe")
        )

        val listDrinkOrder = listOf(
            DrinkOrder(1, "", "Cà phê", "Nhỏ", null, null, 2, 35000f, "Cafe")
        )

        val result = useCase.invoke(voucher, listDrinkOrder, isDelivery = true, subTotal = 20000f)
        assertFalse(result)
    }
}