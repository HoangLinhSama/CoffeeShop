package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.repository.MainRepository
import javax.inject.Inject

class PayWithZaloPayUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(orderZaloPay: com.hoanglinhsama.client.domain.model.OrderZaloPay) {
        mainRepository.createOrderZaloPay(orderZaloPay)
    }
}