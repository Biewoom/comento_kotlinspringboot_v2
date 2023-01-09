package com.comento.jpa.domain.country


data class CountryNotFoundException(override val message: String): RuntimeException(message)