package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.OrderStatus
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderStatusUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(orderId: Int): Flow<Result<OrderStatus>> {
        return mainRepository.getOrderStatus(orderId)
    }
}