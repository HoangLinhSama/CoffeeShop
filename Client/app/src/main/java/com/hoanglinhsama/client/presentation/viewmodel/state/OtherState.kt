package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.FeatureItem
import com.hoanglinhsama.client.domain.model.User

data class OtherState(
    private val _user: User? = null,
    private val _listUtilitiesFeatureItem: List<FeatureItem>? = null,
    private val _listSupportFeatureItem: List<FeatureItem>? = null,
    private val _listAccountFeatureItem: List<FeatureItem>? = null,
) {
    val user = _user
    val listAccountFeatureItem = _listAccountFeatureItem
    val listUtilitiesFeatureItem = _listUtilitiesFeatureItem
    val listSupportFeatureItem = _listSupportFeatureItem
}
