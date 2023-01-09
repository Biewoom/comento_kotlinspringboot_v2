package com.comento.dbless.domain

class Calculator(
    private val expression: List<String>
) {

    val squreList = mutableListOf(Operator.SQUARE)
    val multiplAndDivid = mutableListOf(Operator.MULTIPLE, Operator.DIVIDE)

    init {
        require(expression.size % 2 == 1) { "올바르지 않은 식입니다." }
    }

    fun execute(): Double {
        val firstExecute = operateCalcualte(expression, squreList)
        val secondExcute = operateCalcualte(firstExecute, multiplAndDivid)

        return calculate(secondExcute)
    }

    private fun calculate(exp: List<String>): Double {
        var total = exp[0].toDouble()

        for(i in 1 until exp.size step 2) {
            val operator = Operator.from(exp[i])

            total = operator.apply(total, exp[i+1].toDouble())
        }

        return total
    }

    private fun operateCalcualte(exp: List<String>, operatorList: List<Operator>): List<String> {
        var result = mutableListOf<String>()
        var isSquare = false

        for (i in 1 until exp.size step 2) {
            val operator = Operator.from(exp[i])

            if(operatorList.contains(operator)) {
                val prefixOperand = if(isSquare) result.last().toDouble() else exp[i-1].toDouble()
                val operand = operator.apply(prefixOperand, exp[i+1].toDouble())

                result.removeAt(result.lastIndex)
                result.add(operand.toString())
                isSquare = true
                continue
            }

            if(!isSquare) {
                result.add(exp[i-1])
            }

            result.add(exp[i])
            result.add(exp[i+1])
            isSquare = true
        }

        return result
    }

}
