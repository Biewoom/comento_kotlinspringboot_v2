package com.comento.dbless.ui.dto

import javax.validation.constraints.Positive

data class CalculatorRequest(
    val expression: String,

    @field:Positive
    val round: Int
)
