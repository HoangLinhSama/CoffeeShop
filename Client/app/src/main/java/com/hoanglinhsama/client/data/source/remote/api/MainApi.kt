package com.hoanglinhsama.client.data.source.remote.api

import com.hoanglinhsama.client.data.model.Drink
import com.hoanglinhsama.client.data.model.DrinkCategory
import com.hoanglinhsama.client.data.model.MainResponse
import com.hoanglinhsama.client.data.model.Onboarding
import com.hoanglinhsama.client.data.model.Policies
import com.hoanglinhsama.client.data.model.User
import com.hoanglinhsama.client.data.model.Voucher
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @POST("GetPolicy.php")
    @FormUrlEncoded
    suspend fun getPolicy(
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,
    ): Response<MainResponse<Policies>>

    @POST("UploadAvatar.php")
    @Multipart
    suspend fun upLoadAvatar(@Part pictureAvatar: MultipartBody.Part): Response<MainResponse<String>>

    @POST("Signup.php")
    @FormUrlEncoded
    suspend fun signup(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("image") image: String,
        @Field("storeId") storeId: String,
    ): Response<String>

    @POST("GetUser.php")
    @FormUrlEncoded
    suspend fun getUser(@Field("phone") phone: String): Response<MainResponse<User>>

    @POST("CheckHadAccount.php")
    @FormUrlEncoded
    suspend fun checkHadAccount(@Field("phone") phone: String): Response<MainResponse<Boolean>>

    @POST("GetOnboarding.php")
    @FormUrlEncoded
    suspend fun getOnboarding(
        @Field("page") page: Int,
        @Field("pageSize") pageSize: Int,
    ): Response<MainResponse<Onboarding>>
}