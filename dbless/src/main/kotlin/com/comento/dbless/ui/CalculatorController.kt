package com.comento.dbless.ui

import com.comento.dbless.domain.Calculator
import com.comento.dbless.domain.RandomRange
import com.comento.dbless.ui.dto.CalculatorRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import kotlin.math.pow
import kotlin.math.round

@RestController
@RequestMapping("/api/v1/calculator")
class CalculatorController {

    @GetMapping("/generate/{range}")
    fun randomApi(@PathVariable range: String): ResponseEntity<String> {
        val rangeArr = range.split("~")

        val randomRange = RandomRange(rangeArr[0], rangeArr[1])

        return ResponseEntity.ok(randomRange.create().toString())
    }

    @PostMapping("/calculate")
    fun calculateApi(@RequestBody @Valid request: CalculatorRequest): ResponseEntity<Double> {
        val expression = request.expression.split(" ")

        val calculator = Calculator(expression)
        val roundPow = 10.0.pow(request.round)
        val result = round(calculator.execute() * roundPow) / roundPow

        return ResponseEntity.ok(result)
    }

}
