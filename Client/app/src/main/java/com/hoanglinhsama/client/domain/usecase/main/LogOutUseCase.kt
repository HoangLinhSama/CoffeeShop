package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.domain.repository.MainRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend operator fun invoke() {
        mainRepository.logOut()
    }
}