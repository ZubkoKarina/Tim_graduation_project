package com.example.test.math_actions

import java.lang.Math.sqrt
import kotlin.random.Random

class RootFromNumber {
    fun generateExample(): String {
        val number = Random.nextInt(1, 101)
        return "√$number ="
    }

    fun solveExample(example: String): String {
        val number = example.substring(1, example.length - 2).toInt()
        val root = sqrt(number.toDouble())
        return "%.2f".format(root)
    }
}