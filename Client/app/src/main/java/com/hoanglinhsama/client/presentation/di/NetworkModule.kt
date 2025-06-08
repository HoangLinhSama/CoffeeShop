package com.hoanglinhsama.client.presentation.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hoanglinhsama.client.data.source.remote.api.ApiUtil
import com.hoanglinhsama.client.data.source.remote.api.MainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }

    @Provides
    @ZaloPayOkHttpClient
    @Singleton
    fun provideZaloPayOkHttpClient(spec: ConnectionSpec): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionSpecs(listOf(spec))
            .callTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @RetrofitOkHttpClient
    @Singleton
    fun provideRetrofitOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(10000, TimeUnit.MILLISECONDS)
            .writeTimeout(10000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @RetrofitOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ApiUtil.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}