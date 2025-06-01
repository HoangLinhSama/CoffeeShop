package com.hoanglinhsama.client.domain.usecase.main

import javax.inject.Inject

class UpdateUseBeanUseCase @Inject constructor() {
    operator fun invoke(
        useBean: Boolean,
        currentBean: Int?,
    ): UpdateUseBeanResult {
        return if (useBean && currentBean != null) {
            UpdateUseBeanResult(
                useBean = true,
                discount = currentBean * 1000f,
                toastMessage = "Đã áp dụng đổi bean"
            )
        } else {
            UpdateUseBeanResult(
                useBean = false,
                discount = 0f,
                toastMessage = "Đã hủy áp dụng đổi bean"
            )
        }
    }
}

data class UpdateUseBeanResult(
    val useBean: Boolean,
    val discount: Float,
    val toastMessage: String,
)