package com.hoanglinhsama.client.domain.model

import java.time.LocalDateTime

data class Status(
    private val _name: String,
    private val _description: String,
    private val _image: String,
    private val _dateTime: LocalDateTime,
) {
    val name = _name
    val description = _description
    val image = _image
    val dateTime = _dateTime
}
