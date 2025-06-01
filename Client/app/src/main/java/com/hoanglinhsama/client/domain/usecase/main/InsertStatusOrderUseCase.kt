package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertStatusOrderUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(orderId: Int, statusId: Int): Flow<Result<Unit>> {
        return mainRepository.insertStatusOrder(orderId, statusId)
    }
}