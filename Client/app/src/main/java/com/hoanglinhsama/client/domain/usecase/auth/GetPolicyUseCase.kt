package com.hoanglinhsama.client.domain.usecase.auth

import androidx.paging.PagingData
import com.hoanglinhsama.client.domain.model.Policies
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPolicyUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke():Flow<PagingData<Policies>> {
        return authRepository.getPolicy()
    }

}