package com.hoanglinhsama.client.domain.model

data class Policies(private val _title: String, private val _content: String) {
    val title = _title
    val content = _content
}
