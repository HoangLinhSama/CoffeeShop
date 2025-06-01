package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePaymentBillIdUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(
        orderId: Int,
        paymentBillId: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Result<Unit>> {
        return mainRepository.updatePaymentBillId(orderId, paymentBillId, callback)
    }
}