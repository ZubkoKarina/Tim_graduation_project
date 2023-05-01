package com.example.test.math_actions
import kotlin.random.Random

class FindX {
    private val random = Random.Default

    private fun randomOperation(): String {
        return when (random.nextInt(1, 5)) {
            1 -> "+"
            2 -> "-"
            3 -> "*"
            4 -> "/"
            else -> throw IllegalStateException("Invalid operation.")
        }
    }

    fun generateExample(): Pair<String, Double> {
        val numElements = random.nextInt(2, 5)
        val elements = mutableListOf<String>()

        var xValue = 0.0
        for (i in 1..numElements) {
            if (i % 2 == 0) {
                elements.add(randomOperation())
            } else {
                if (i == numElements - 1) {
                    elements.add("x")
                    xValue = random.nextDouble(1.0, 10.0)
                } else {
                    elements.add(random.nextInt(1, 11).toString())
                }
            }
        }
        return Pair(elements.joinToString(" "), xValue)
    }

    fun solveExample(examplePair: Pair<String, Double>): Double {
        val tokens = examplePair.first.split(" ")
        val values = mutableListOf<Double>()
        val ops = mutableListOf<String>()

        for (token in tokens) {
            when (token) {
                "+" -> ops.add(token)
                "-" -> ops.add(token)
                "*" -> ops.add(token)
                "/" -> ops.add(token)
                "x" -> values.add(examplePair.second)
                else -> values.add(token.toDouble())
            }
        }

        for (i in 0 until ops.size) {
            val op = ops[i]
            val a = values[i]
            val b = values[i + 1]

            when (op) {
                "+" -> values[i + 1] = a + b
                "-" -> values[i + 1] = a - b
                "*" -> values[i + 1] = a * b
                "/" -> {
                    if (b == 0.0) {
                        throw ArithmeticException("Division by zero.")
                    }
                    values[i + 1] = a / b
                }
            }
        }
        return values.last()
    }
}