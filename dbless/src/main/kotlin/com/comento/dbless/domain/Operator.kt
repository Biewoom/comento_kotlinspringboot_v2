package com.comento.dbless.domain

import java.util.function.BinaryOperator
import kotlin.math.pow

enum class Operator(
    val value: String
): BinaryOperator<Double> {

    PLUS("+") {
        override fun apply(number1: Double, number2: Double): Double = number1 + number2
    },
    MINUS("-") {
        override fun apply(number1: Double, number2: Double): Double = number1 - number2
    },
    MULTIPLE("*") {
        override fun apply(number1: Double, number2: Double): Double = number1 * number2
    },
    DIVIDE("/") {
        override fun apply(number1: Double, number2: Double): Double {
            require(number2 != 0.0) { "0으로는 나눌 수 없습니다" }
            return number1 / number2
        }
    },
    SQUARE("^") {
        override fun apply(number1: Double, number2: Double): Double = number1.pow(number2)
    };

    companion object {
        fun from(operator: String): Operator {
            return values()
                .find { it.value == operator } ?: throw IllegalArgumentException("올바르지 않은 연산자 입니다")
        }
    }

}
