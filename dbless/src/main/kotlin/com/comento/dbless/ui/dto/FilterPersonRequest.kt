package com.comento.dbless.ui.dto

import com.comento.dbless.domain.Person
import javax.validation.Valid

data class FilterPersonRequest(
    val ageCutoff: Int?,
    val heightCutoff: Int?,
    val except: List<String>?,

    @field:Valid
    val persons: List<Person>
)
