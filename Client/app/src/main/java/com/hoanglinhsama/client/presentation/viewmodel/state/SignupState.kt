package com.hoanglinhsama.client.presentation.viewmodel.state

import androidx.paging.PagingData
import com.hoanglinhsama.client.data.model.Result
import com.hoanglinhsama.client.domain.model.Policies
import kotlinx.coroutines.flow.Flow

data class SignupState(
    private val _firstName: String = "",
    private val _lastName: String = "",
    private val _address: String = "",
    private val _uriAvatar: String = "",
    private val _listTextFieldFocus: List<Boolean> = List(6) { false },
    private val _isChecked: Boolean = false,
    private val _itemsPolicy: Flow<PagingData<Policies>>? = null,
    private val _isReadPolicy: Boolean = false,
    private val _signupResultFlow: Flow<Result<Unit>>? = null,
    private val _resultSignup: Result<Unit>? = null,
) {
    val firstName = _firstName
    val lastName = _lastName
    val address = _address
    val uriAvatar = _uriAvatar
    val listTextFieldFocus = _listTextFieldFocus
    val isChecked = _isChecked
    val itemsPolicy = _itemsPolicy
    val isReadPolicy = _isReadPolicy
    val signupResultFlow = _signupResultFlow
    val resultSignup = _resultSignup
}

