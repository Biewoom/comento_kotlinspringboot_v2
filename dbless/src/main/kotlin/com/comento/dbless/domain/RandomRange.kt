package com.comento.dbless.domain

import kotlin.math.round
import kotlin.random.Random

class RandomRange(
    val startNumber: String,
    val endNumber: String
) {

    private val random: Random = Random

    fun create(): Number {
        try {
            return startNumber.toInt() + random.nextInt(endNumber.toInt() - startNumber.toInt())
        } catch (e: NumberFormatException) {
            val result = startNumber.toDouble() + random.nextDouble(endNumber.toDouble() - startNumber.toDouble())
            return round( result * 10000 ) / 10000
        }
    }

}
