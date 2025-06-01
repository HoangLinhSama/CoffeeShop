package com.hoanglinhsama.client.domain.repository

import androidx.paging.PagingData
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Policies
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AuthRepository {
    fun getPolicy(): Flow<PagingData<Policies>>

    fun uploadAvatar(
        multipartBody: MultipartBody.Part,
    ): Flow<Result<String>>

    fun signup(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        image: String,
    ): Flow<Result<Unit>>

    fun checkHadAccount(
        phone: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Unit>

    suspend fun savePhone(phone: String, callback: () -> Unit)
    suspend fun updateStateRememberPhone(isRemember: Boolean)
    fun autoFillPhone(): Flow<Boolean>
}