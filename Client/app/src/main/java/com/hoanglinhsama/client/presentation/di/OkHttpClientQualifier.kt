package com.hoanglinhsama.client.presentation.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class RetrofitOkHttpClient

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ZaloPayOkHttpClient