package com.comento.dbless.domain

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

data class Person(
    @field:Min(0)
    @field:Max(100)
    val age: Int,

    @field:Min(130)
    @field:Max(200)
    val height: Int,

    val id: String,

    @field:Pattern(regexp = "^[a-zA-Z]*$")
    val name: String
)
