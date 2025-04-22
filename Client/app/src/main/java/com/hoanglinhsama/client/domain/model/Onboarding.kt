package com.hoanglinhsama.client.domain.model

data class Onboarding(
    private val _image: String,
    private val _title: String,
    private val _description: String,
) {
    val image = _image
    val title = _title
    val description = _description
}
