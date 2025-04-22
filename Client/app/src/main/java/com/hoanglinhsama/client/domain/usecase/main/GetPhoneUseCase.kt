package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhoneUseCase @Inject constructor(private val mainRepository: MainRepository) {
    operator fun invoke(): Flow<String> {
        return mainRepository.getPhone()
    }
}