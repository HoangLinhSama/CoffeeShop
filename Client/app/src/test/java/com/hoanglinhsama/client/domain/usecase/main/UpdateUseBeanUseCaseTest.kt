package com.hoanglinhsama.client.domain.usecase.main

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UpdateUseBeanUseCaseTest {
    private val useCase = UpdateUseBeanUseCase()

    @Test
    fun shouldReturnDiscountAndMessage_whenUseBeanIsTrueAndCurrentBeanIsNotNull() {
        val result = useCase(useBean = true, currentBean = 5)
        assertThat(result).isEqualTo(
            UpdateUseBeanResult(
                useBean = true,
                discount = 5000f,
                toastMessage = "Đã áp dụng đổi bean"
            )
        )
    }

    @Test
    fun shouldReturnZeroDiscountAndCancelMessage_whenUseBeanIsFalse() {
        val result = useCase(useBean = false, currentBean = 5)
        assertThat(result).isEqualTo(
            UpdateUseBeanResult(
                useBean = false,
                discount = 0f,
                toastMessage = "Đã hủy áp dụng đổi bean"
            )
        )
    }

    @Test
    fun shouldReturnZeroDiscountAndCancelMessage_whenCurrentBeanIsNull() {
        val result = useCase(useBean = true, currentBean = null)

        assertThat(result).isEqualTo(
            UpdateUseBeanResult(
                useBean = false,
                discount = 0f,
                toastMessage = "Đã hủy áp dụng đổi bean"
            )
        )
    }
}