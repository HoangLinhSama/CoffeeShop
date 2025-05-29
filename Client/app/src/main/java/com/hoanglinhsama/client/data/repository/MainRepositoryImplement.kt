package com.hoanglinhsama.client.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.hoanglinhsama.client.data.mapper.toOrderStatusDomain
import com.hoanglinhsama.client.data.mapper.toUserDomain
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.data.model.UniqueResult
import com.hoanglinhsama.client.data.source.paging.DrinkCategoryPagingSource
import com.hoanglinhsama.client.data.source.paging.DrinkPagingSource
import com.hoanglinhsama.client.data.source.paging.ShopPagingSource
import com.hoanglinhsama.client.data.source.paging.VoucherPagingSource
import com.hoanglinhsama.client.data.source.paging.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.DrinkOrder
import com.hoanglinhsama.client.domain.model.OrderStatus
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImplement @Inject constructor(
    private val mainApi: MainApi,
    private val userSettingDataStore: DataStore<Preferences>,
) : MainRepository {
    override fun getPromotion(phone: String): Flow<PagingData<Voucher>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 3, pageSize = 3),
            pagingSourceFactory = {
                VoucherPagingSource(mainApi = mainApi, phone)
            }).flow
    }


    override fun getDrinkCategory(): Flow<PagingData<DrinkCategory>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 3, pageSize = 3),
            pagingSourceFactory = {
                DrinkCategoryPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun getDrink(): Flow<PagingData<Drink>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 10, pageSize = 10),
            pagingSourceFactory = {
                DrinkPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun getUser(phone: String): Flow<Result<User>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.getUser(phone)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.toUserDomain()?.let {
                            emit(Result.Success(it))
                        }
                    } else if (response.body()?.status == "fail: no data found") {
                        emit(Result.Error(Exception(response.body()?.status)))
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

    override fun getPhone(): Flow<String> {
        return userSettingDataStore.data.map {
            it[PreferenceKey.PHONE] ?: ""
        }
    }

    override suspend fun updateStateLogIn() {
        userSettingDataStore.edit {
            it[PreferenceKey.LOGGED_IN] = true
        }
    }

    override suspend fun logOut() {
        userSettingDataStore.edit {
            it[PreferenceKey.LOGGED_IN] = false
        }
    }

    override fun getShop(): Flow<PagingData<Shop>> {
        return Pager(
            config = PagingConfig(initialLoadSize = 5, pageSize = 5),
            pagingSourceFactory = {
                ShopPagingSource(mainApi = mainApi)
            }).flow
    }

    override fun getRequiredBean(phone: String): Flow<Result<Int>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.getRequiredBean(phone)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.let {
                            emit(Result.Success(it))
                        }
                    } else if (response.body()?.status == "fail: no data found") {
                        emit(Result.Error(Exception(response.body()?.status)))
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

    override fun insertOrder(
        userId: Int,
        name: String?,
        phone: String?,
        address: String?,
        dateTime: String,
        quantityBeanUse: Int?,
        paymentMethod: String,
        shopId: Int?,
        isDelivery: Boolean,
        deliveryFee: Float,
        subTotal: Float,
        totalPrice: Float,
        voucherId: Int?,
        paymentBillId: String?,
        listDrinkOrder: List<com.hoanglinhsama.client.data.model.DrinkOrder>,
    ): Flow<Result<Int>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.insertOrder(
                    userId,
                    name,
                    phone,
                    address,
                    dateTime,
                    quantityBeanUse,
                    paymentMethod,
                    shopId,
                    isDelivery,
                    deliveryFee,
                    subTotal,
                    totalPrice,
                    voucherId,
                    paymentBillId,
                    Gson().toJson(listDrinkOrder)
                )
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.let {
                            emit(Result.Success(it))
                        }
                    } else if (response.body()?.status == "fail: unknown error") {
                        emit(Result.Error(Exception(response.body()?.status)))
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getOrderStatus(orderId: Int): Flow<Result<OrderStatus>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.getOrderStatus(orderId)
                if (response.isSuccessful) {
                    if (response.body()?.status == "success") {
                        response.body()?.result?.get(0)?.let {
                            emit(Result.Success(it.toOrderStatusDomain()))
                        }
                    } else if (response.body()?.status == "fail: unknown error") {
                        emit(Result.Error(Exception(response.body()?.status)))
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

    override fun createOrderZaloPay(pay: com.hoanglinhsama.client.domain.model.OrderZaloPay) {

    }
}