package com.hoanglinhsama.client.domain.usecase.main

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPromotionUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(phone: String): Flow<PagingData<Voucher>> {
        return mainRepository.getPromotion(phone)
    }
}
