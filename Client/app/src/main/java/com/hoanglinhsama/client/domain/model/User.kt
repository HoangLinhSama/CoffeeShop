package com.hoanglinhsama.client.domain.model

data class User(
    var id: Int,
    var name: String,
    var phone: String,
    var address: String,
    var coffeeBean: Int?,
    var membership: String?,
    var storeId: Int?,
    var type: Int?
)
