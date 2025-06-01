package com.hoanglinhsama.client.presentation.di

import android.app.Application
import com.hoanglinhsama.client.data.source.remote.zalopay.config.AppInfo
import dagger.hilt.android.HiltAndroidApp
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

@HiltAndroidApp
class MyApplication : Application()