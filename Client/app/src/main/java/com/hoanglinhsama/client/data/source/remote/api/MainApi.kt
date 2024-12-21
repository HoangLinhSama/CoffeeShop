package com.hoanglinhsama.client.data.source.remote.api

import com.hoanglinhsama.client.data.model.Drink
import com.hoanglinhsama.client.data.model.DrinkCategory
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Voucher
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MainApi {
    @FormUrlEncoded
    @POST("GetPromotion.php")
    suspend fun getPromotion(
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,
    ): Response<MainResponse<Voucher>>

    @FormUrlEncoded
    @POST("GetDrinkCategory.php")
    suspend fun getDrinkCategory(
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,
    ): Response<MainResponse<DrinkCategory>>

    @POST("GetDrink.php")
    @FormUrlEncoded
    suspend fun getDrink(
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,
    ): Response<MainResponse<Drink>>
}