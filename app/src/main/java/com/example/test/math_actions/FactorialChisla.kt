package com.example.test.math_actions

import kotlin.random.Random

class FactorialChisla {
    private val random = Random.Default

    fun generateExample(): Int {
        return random.nextInt(1, 11) // Генерує випадкове число від 1 до 10
    }

    fun solveExample(example: Int): Long {
        return factorial(example)
    }

    private fun factorial(n: Int): Long {
        return if (n == 1) 1 else n * factorial(n - 1)
    }
}