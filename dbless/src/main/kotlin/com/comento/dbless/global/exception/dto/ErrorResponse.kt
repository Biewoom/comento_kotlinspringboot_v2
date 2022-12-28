package com.comento.dbless.global.exception.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val statusCode: String,
    val message: String
)
