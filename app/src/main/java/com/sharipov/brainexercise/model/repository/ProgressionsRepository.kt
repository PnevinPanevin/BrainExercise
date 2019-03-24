package com.sharipov.brainexercise.model.repository

import kotlin.random.Random

object ProgressionsRepository {
    private const val ARITHMETIC_START_BOUND = 20
    private const val GEOMETRIC_START_BOUND = 4
    private const val ARITHMETIC_REMAINDER_BOUND = 10
    private const val GEOMETRIC_REMAINDER_BOUND = 5
    private const val PROGRESSIONS_COUNT = 300

    private val random: Random = Random(System.currentTimeMillis())

    fun getProgressions(): List<Progression> = List(PROGRESSIONS_COUNT) {
        getProgression()
    }

    //0 - arithmetic, 1 - geometric
    private fun getBaseType(): Int = random.nextInt(1)

    private fun getStart(type: Int): Int {
        var start = 0
        do {
            start = if (type == 0) {
                random.nextInt(ARITHMETIC_START_BOUND)
            } else {
                random.nextInt(GEOMETRIC_START_BOUND)
            }
        } while (start < 0)
        return start
    }


    private fun getRemainder(type: Int): Int =
        if (type == 0) {
            random.nextInt(ARITHMETIC_REMAINDER_BOUND)
        } else {
            random.nextInt(GEOMETRIC_REMAINDER_BOUND)
        }

    private fun getElementByIndex(p: ProgressionBase, index: Int): String = when (p.type) {
        0 -> (p.start + (index - 1) * p.remainder).toString()
        else -> (p.start * Math.pow(p.remainder.toDouble(), (index - 1).toDouble())).toString()
    }

    private fun getUnknownElement(): Int {
        return random.nextInt(4)
    }

    private fun getProgression(): Progression {
        val type = getBaseType()
        val unknownElement = getUnknownElement()
        val p = ProgressionBase(getStart(type), getRemainder(type))
        val elements = List(5) {
            if (it == unknownElement) {
                "?"
            } else {
                getElementByIndex(p, it)
            }
        }
        return Progression(elements, getElementByIndex(p, unknownElement))
    }
}

data class ProgressionBase(
    val start: Int = 0,
    val remainder: Int = 1,
    val type: Int = 0
)

data class Progression(
    val elements: List<String>,
    val answer: String
)