package com.hoanglinhsama.client.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.paging.PolicyPagingSource
import com.hoanglinhsama.client.data.source.remote.api.ApiUtil
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Policies
import com.hoanglinhsama.client.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class AuthRepositoryImplement @Inject constructor(
    private val mainApi: MainApi,
    private val userSettingDataStore: DataStore<Preferences>,
) : AuthRepository {
    override fun getPolicy(): Flow<PagingData<Policies>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 4, pageSize = 4),
            pagingSourceFactory = {
                PolicyPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun uploadAvatar(
        multipartBody: MultipartBody.Part,
    ): Flow<Result<String>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.upLoadAvatar(multipartBody)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        emit(
                            Result.Success(
                                ApiUtil.BASE_URL + "picture/avatar/" + response.body()?.result?.get(
                                    0
                                )
                            )
                        )
                    } else if (response.body()?.status == "fail") {
                        emit(
                            Result.Error(
                                Exception(
                                    response.body()?.status
                                )
                            )
                        )
                    } else {
                        emit(Result.Error(Exception(response.body()?.status)))
                    }
                } else {
                    emit(Result.Error(Exception(("API request failed with status: ${response.code()}"))))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    override fun signup(
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        image: String,
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.signup(firstName, lastName, phone, address, image, "")
                if (response.isSuccessful) {
                    if (response.body() == "success") {
                        emit(Result.Success(Unit))
                    } else if (response.body() == "fail") {
                        emit(Result.Error(Exception(response.body())))
                    } else {
                        emit(Result.Error(Exception(response.body())))
                    }
                } else {
                    emit(Result.Error(Exception(("API request failed with status: ${response.code()}"))))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }

    override fun checkHadAccount(
        phone: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.checkHadAccount(phone)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.let {
                            callback(response.body()?.status ?: "", it)
                            emit(Result.Success(Unit))
                        }
                    } else if (response.body()?.status == "fail") {
                        callback(response.body()?.status ?: "", null)
                        emit(Result.Error(Exception(response.body()?.status)))
                    } else {
                        callback(response.body()?.status ?: "", null)
                        emit(Result.Error(Exception(response.body()?.status)))
                    }
                } else {
                    callback("API request failed with status: ${response.code()}", null)
                    emit(Result.Error(Exception(("API request failed with status: ${response.code()}"))))
                }
            } catch (e: Exception) {
                callback("${e.message}", null)
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun savePhone(phone: String, callback: () -> Unit) {
        userSettingDataStore.edit {
            it[PreferenceKey.PHONE] = phone
        }
        callback()
    }

    override suspend fun updateStateRememberPhone(isRemember: Boolean) {
        userSettingDataStore.edit {
            it[PreferenceKey.CHECKED_SAVE_PHONE] = isRemember
        }
    }

    override fun autoFillPhone(): Flow<Boolean> {
        return userSettingDataStore.data.map {
            it[PreferenceKey.CHECKED_SAVE_PHONE] == true
        }
    }
}