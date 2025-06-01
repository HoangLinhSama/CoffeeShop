package com.hoanglinhsama.client.domain.usecase.main

import com.hoanglinhsama.client.data.source.remote.zalopay.config.AppInfo
import com.hoanglinhsama.client.data.source.remote.zalopay.helpers.Helpers
import com.hoanglinhsama.client.domain.model.OrderZaloPay
import com.hoanglinhsama.client.domain.repository.MainRepository
import org.json.JSONObject
import javax.inject.Inject

class CreateOrderZaloPayUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend operator fun invoke(
        appUser: String,
        orderId: Int,
        amount: Long,
        items: String,
    ): JSONObject? {
        val appTransId = Helpers.getAppTransId(orderId)
        val appTime = System.currentTimeMillis()
        val description = "CoffeeShop thanh toán đơn hàng #$appTransId"
        val embedData = "{}"
        val inputHMac = listOf(
            AppInfo.APP_ID,
            appTransId,
            appUser,
            amount.toString(),
            appTime,
            embedData,
            items
        ).joinToString("|")
        val mac = Helpers.getMac(AppInfo.MAC_KEY, inputHMac)
        return mainRepository.createOrderZaloPay(
            OrderZaloPay(
                AppInfo.APP_ID,
                appUser,
                appTransId,
                appTime,
                amount,
                items,
                description,
                embedData,
                "zalopayapp",
                mac
            )
        )
    }
}