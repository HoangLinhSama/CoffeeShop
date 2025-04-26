package com.hoanglinhsama.client.domain.usecase.main

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShopUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(): Flow<PagingData<Shop>> {
        return mainRepository.getShop()
    }
}