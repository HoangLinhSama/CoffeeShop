package com.hoanglinhsama.client.presentation.di

import android.app.Application
import com.hoanglinhsama.client.data.config.AppInfo
import dagger.hilt.android.HiltAndroidApp
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX)
    }
}