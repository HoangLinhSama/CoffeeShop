package com.hoanglinhsama.client.data.source.remote.zalopay.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Helpers {
    private var resetOrderId = 1

    @SuppressLint("SimpleDateFormat", "DefaultLocale")
    fun getAppTransId(orderId: Int): String {
        val formatDateTime = SimpleDateFormat("yyMMdd", Locale.getDefault())
        formatDateTime.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        val time = formatDateTime.format(Date())
        return String.format("%s_%06d", time, if (orderId >= 1000000) resetOrderId else orderId)
    }

    fun getMac(key: String, data: String): String {
        return requireNotNull(HMacUtil.hMacHexStringEncode(HMacUtil.H_MAC_SHA_256, key, data))
    }
}