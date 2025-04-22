package com.hoanglinhsama.client.domain.usecase.app

import com.hoanglinhsama.client.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckSignedInUseCase @Inject constructor(private val appRepository: AppRepository) {
    operator fun invoke(): Flow<Boolean> {
        return appRepository.checkSignedIn()
    }
}