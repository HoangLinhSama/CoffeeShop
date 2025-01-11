package com.hoanglinhsama.client.presentation.viewmodel.state

import com.hoanglinhsama.client.domain.model.LoginMethod

data class LoginState(
    private val _listMethodLogin: List<LoginMethod>? = null,
    private val _listIsFocus: List<Boolean> = List(2) { false },
    private val _phoneNumber: String = "",
    private val _password: String = "",
    private val _isShowPassword: Boolean = false,
    private val _isCheckRemember: Boolean = false,
) {
    val listMethodLogin = _listMethodLogin
    val listIsFocus = _listIsFocus
    val phoneNumber = _phoneNumber
    val password = _password
    val isShowPassword = _isShowPassword
    val isCheckRemember = _isCheckRemember
}