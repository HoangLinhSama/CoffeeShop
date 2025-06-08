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
import com.hoanglinhsama.client.data.source.local.preferences.PreferenceKey
import com.hoanglinhsama.client.data.source.paging.DrinkCategoryPagingSource
import com.hoanglinhsama.client.data.source.paging.DrinkPagingSource
import com.hoanglinhsama.client.data.source.paging.ShopPagingSource
import com.hoanglinhsama.client.data.source.paging.VoucherPagingSource
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import com.hoanglinhsama.client.data.source.remote.zalopay.HttpProvider
import com.hoanglinhsama.client.data.source.remote.zalopay.config.AppInfo
import com.hoanglinhsama.client.domain.model.Drink
import com.hoanglinhsama.client.domain.model.DrinkCategory
import com.hoanglinhsama.client.domain.model.OrderStatus
import com.hoanglinhsama.client.domain.model.OrderZaloPay
import com.hoanglinhsama.client.domain.model.Shop
import com.hoanglinhsama.client.domain.model.User
import com.hoanglinhsama.client.domain.model.Voucher
import com.hoanglinhsama.client.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.FormBody
import org.json.JSONObject
import javax.inject.Inject

class MainRepositoryImplement @Inject constructor(
    private val mainApi: MainApi,
    private val userSettingDataStore: DataStore<Preferences>,
    private val httpProvider: HttpProvider,
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

    override suspend fun createOrderZaloPay(orderZaloPay: OrderZaloPay): JSONObject? {
        val formBody = FormBody.Builder()
            .add("app_id", orderZaloPay.appId.toString())
            .add("app_user", orderZaloPay.appUser)
            .add("app_trans_id", orderZaloPay.appTransId)
            .add("app_time", orderZaloPay.appTime.toString())
            .add("amount", orderZaloPay.amount.toString())
            .add("item", orderZaloPay.item)
            .add("description", orderZaloPay.description)
            .add("embed_data", orderZaloPay.embedData)
            .add("bank_code", orderZaloPay.bankCode)
            .add("mac", orderZaloPay.mac)
            .build()
        return httpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody)
    }

    override fun insertStatusOrder(orderId: Int, statusId: Int): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.insertStatusOrder(orderId, statusId)
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

    override fun updatePaymentBillId(
        orderId: Int,
        paymentBillId: String,
        callback: (String, Boolean?) -> Unit,
    ): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = mainApi.updatePaymentBillId(orderId, paymentBillId)
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
}