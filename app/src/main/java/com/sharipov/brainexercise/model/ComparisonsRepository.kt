package com.sharipov.brainexercise.model

import kotlin.random.Random

object ComparisonsRepository {
    private const val CARDS_QUANTITY = 200
    private const val DIVISION_MULTIPLICATION_BOUNDS_INIT_VALUE = 5
    private const val ADDITION_SUBTRACTION_BOUNDS_INIT_VALUE = 15
    private const val DIFFERENCE_BOUNDS = 5

    private var divisionMultiplicationBounds: Int = DIVISION_MULTIPLICATION_BOUNDS_INIT_VALUE
    private var additionSubtractionBounds: Int = ADDITION_SUBTRACTION_BOUNDS_INIT_VALUE

    private val random: Random = Random.Default
    private val cardList = ArrayList<Comparison>()

    fun getCardList(): List<Comparison> {
        cardList.clear()
        for (i in 1..CARDS_QUANTITY) {
            cardList += generate(random.nextInt(4))
        }
        return cardList
    }

    private fun generate(cardType: Int) = when (cardType) {
        0 -> generateAdditionCard()
        1 -> generateSubtractionCard()
        2 -> generateMultiplicationCard()
        3 -> generateDivisionCard()
        else -> generateAdditionCard()
    }

    private fun generateDivisionCard(): Comparison {
        val result = random.nextInt(divisionMultiplicationBounds)
        var secondInt = random.nextInt(divisionMultiplicationBounds)
        if (secondInt == 0) {
            secondInt++
        }
        val firstInt = result * secondInt

        val expression = getExpression(firstInt, "/", secondInt)
        val second = getGeneratedSecond(result)
        val answer = result.compareTo(second)
        return Comparison(expression, second.toString(), answer)
    }

    private fun generateMultiplicationCard(): Comparison {
        val firstInt = random.nextInt(divisionMultiplicationBounds)
        val secondInt = random.nextInt(divisionMultiplicationBounds)
        val result = firstInt * secondInt

        val first = getExpression(firstInt, "*", secondInt)
        val second = getGeneratedSecond(result)
        val answer = result.compareTo(second)
        return Comparison(first, second.toString(), answer)
    }

    private fun generateSubtractionCard(): Comparison {
        val result = random.nextInt(additionSubtractionBounds)
        val secondInt = random.nextInt(additionSubtractionBounds)
        val firstInt = result + secondInt

        val first = getExpression(firstInt, "-", secondInt)
        val second = getGeneratedSecond(result)
        val answer = result.compareTo(second)

        return Comparison(first, second.toString(), answer)
    }

    private fun generateAdditionCard(): Comparison {
        val firstInt = random.nextInt(additionSubtractionBounds)
        val secondInt = random.nextInt(additionSubtractionBounds)
        val result = firstInt + secondInt

        val first = getExpression(firstInt, "+", secondInt)
        val second = getGeneratedSecond(result)
        val answer = result.compareTo(second)
        return Comparison(first, second.toString(), answer)
    }

    private fun getGeneratedSecond(result: Int): Int {
        val difference = random.nextInt(-DIFFERENCE_BOUNDS, DIFFERENCE_BOUNDS)
        return result + difference
    }

    private fun getExpression(first: Int, sign: String, second: Int): String = "$first $sign $second = ?"
}

data class Comparison(
    val first: String,
    val second: String,
    val answer: Int
)