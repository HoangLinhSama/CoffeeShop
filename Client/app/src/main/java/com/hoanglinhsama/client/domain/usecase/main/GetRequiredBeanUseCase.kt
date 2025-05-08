package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRequiredBeanUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(phone: String): Flow<Result<Int>> {
        return mainRepository.getRequiredBean(phone)
    }
}