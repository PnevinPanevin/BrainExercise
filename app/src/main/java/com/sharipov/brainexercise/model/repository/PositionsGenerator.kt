package com.sharipov.brainexercise.model.repository

import kotlin.random.Random

object PositionsGenerator{
    private const val POSITIONS_COUNT = 300
    private const val POSITIONS_BOUND = 3
    private const val CARD_LINE_LENGTH = 5
    private val random: Random = Random(System.currentTimeMillis())

    private fun getAnswer(j: Int, currentPosition: Int, previousPosition: Int) =
        if ((j != 0) || (currentPosition == previousPosition)) {
            Answer.YES
        } else {
            Answer.NO
        }

    fun getPositions(): List<Position>{
        val positions = ArrayList<Position>()
        var previousCardType = 100 //init value out of checking bounds
        for (i in 0..POSITIONS_COUNT) {
            val newLineOfCards = random.nextInt(CARD_LINE_LENGTH)
            val currentPosition = random.nextInt(POSITIONS_BOUND)
            for (j in 0..newLineOfCards) {
                val answer = getAnswer(j, currentPosition, previousCardType)
                positions += Position(currentPosition, answer)
                previousCardType = currentPosition
            }
        }
        return positions
    }
}

data class Position(
    val position: Int,
    val answer: Answer
)