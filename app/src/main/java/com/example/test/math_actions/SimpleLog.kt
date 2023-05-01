package com.example.test.math_actions

import java.lang.Math.log10

class SimpleLog {
    fun generateExample(): Pair<Double, Double> {
        val number = (1..100).random().toDouble()
        val base = (1..10).random().toDouble()
        return Pair(number, base)
    }

    fun solveExample(example: Pair<Double, Double>): String {
        return (log10(example.first) / log10(example.second)).toFloat().format(3)
    }

    fun Float.format(digits: Int) = "%.${digits}f".format(this)

}