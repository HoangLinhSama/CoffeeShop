package com.hoanglinhsama.client.domain.model

data class OrderZaloPay(
    private val _appId: Int,
    private val _appUser: String,
    private val _appTransId: String,
    private val _appTime: Long,
    private val _amount: Long,
    private val _item: String,
    private val _description: String,
    private val _embedData: String,
    private val _bankCode: String,
    private val _mac: String,
) {
    val appId = _appId
    val appUser = _appUser
    val appTransId = _appTransId
    val appTime = _appTime
    val amount = _amount
    val item = _item
    val description = _description
    val embedData = _embedData
    val bankCode = _bankCode
    val mac = _mac
}
