package com.sharipov.brainexercise.model.repository

import kotlin.random.Random

object ExpressionsRepository {
    private const val CARDS_QUANTITY = 200
    private const val DIVISION_MULTIPLICATION_BOUNDS_INIT_VALUE = 5
    private const val ADDITION_SUBTRACTION_BOUNDS_INIT_VALUE = 15

    private var divisionMultiplicationBounds: Int =
        DIVISION_MULTIPLICATION_BOUNDS_INIT_VALUE
    private var additionSubtractionBounds: Int =
        ADDITION_SUBTRACTION_BOUNDS_INIT_VALUE

    private val random: Random = Random.Default
    private val cardList = ArrayList<Expression>()

    fun getCardList(): List<Expression> {
        cardList.clear()
        for (i in 1..CARDS_QUANTITY) {
            cardList += generate(
                random.nextInt(4)
            )
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

    private fun generateDivisionCard(): Expression {
        val result = random.nextInt(divisionMultiplicationBounds)
        var secondInt = random.nextInt(divisionMultiplicationBounds)
        if (secondInt == 0)
            secondInt++
        val firstInt = result * secondInt
        val expression = "$firstInt / $secondInt = ?"
        return Expression(expression, result)
    }

    private fun generateMultiplicationCard(): Expression {
        val firstInt = random.nextInt(divisionMultiplicationBounds)
        val secondInt = random.nextInt(divisionMultiplicationBounds)
        val result = firstInt * secondInt
        val expression = "$firstInt * $secondInt = ?"
        return Expression(expression, result)
    }

    private fun generateSubtractionCard(): Expression {
        val result = random.nextInt(additionSubtractionBounds)
        val secondInt = random.nextInt(additionSubtractionBounds)
        val firstInt = result + secondInt
        val expression = "$firstInt - $secondInt = ?"
        return Expression(expression, result)
    }

    private fun generateAdditionCard(): Expression {
        val firstInt = random.nextInt(additionSubtractionBounds)
        val secondInt = random.nextInt(additionSubtractionBounds)
        val result = firstInt + secondInt
        val expression = "$firstInt + $secondInt = ?"
        return Expression(expression, result)
    }
}

data class Expression(val expression: String, val answer: Int)