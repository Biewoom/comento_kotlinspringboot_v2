package com.comento.dbless.global.exception

import com.comento.dbless.global.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice{

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun invalidRequestBodyException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val message = e.allErrors.map { it.defaultMessage }.get(0).toString()
        val response = ErrorResponse(HttpStatus.BAD_REQUEST.name, message)

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}
