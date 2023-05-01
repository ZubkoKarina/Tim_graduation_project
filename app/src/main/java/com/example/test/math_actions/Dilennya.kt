package com.example.test.math_actions

import kotlin.random.Random

class Dilennya {
    fun generateExample(): String {
        val firstNumber = Random.nextInt(1, 100)
        val secondNumber = Random.nextInt(1, 100)
        return "$firstNumber / $secondNumber ="
    }
    fun solveExample(example: String): Int {
        val numbers = example.split(" /").map { it.trim() }
        val firstNumber = numbers[0].toInt()
        val secondNumber = numbers[1].substringBefore(" =").toInt()
        return firstNumber / secondNumber
    }
}