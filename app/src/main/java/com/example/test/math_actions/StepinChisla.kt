package com.example.test.math_actions
import java.io.File
import kotlin.math.pow

class StepinChisla {
    private val random = java.util.Random()

    fun generateExample(): Pair<Int, Int> {
        val base = random.nextInt(9) + 2
        val exponent = random.nextInt(4) + 2
        return base to exponent
    }

    fun solveExample(example: Pair<Int, Int>): Int {
        return example.first.toDouble().pow(example.second).toInt()
    }
}
