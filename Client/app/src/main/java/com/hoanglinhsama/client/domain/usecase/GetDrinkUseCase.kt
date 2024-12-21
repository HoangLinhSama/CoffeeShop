package com.hoanglinhsama.client.domain.usecase

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDrinkUseCase @Inject constructor(val mainRepository: MainRepository) {
    operator fun invoke(): Flow<PagingData<Drink>> {
        return mainRepository.getDrink()
    }
}