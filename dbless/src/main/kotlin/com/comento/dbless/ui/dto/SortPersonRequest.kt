package com.comento.dbless.ui.dto

import com.comento.dbless.domain.Person
import javax.validation.Valid

data class SortPersonRequest(

    @field:Valid
    val persons: List<Person>,
    val sortBy: String,
    val sortOrder: String
)
