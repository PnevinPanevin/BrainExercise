package com.sharipov.brainexercise.model

import com.sharipov.brainexercise.R
import java.util.*

object CardsRepository {
    private const val CARD_LINE_LENGTH = 5
    private const val CARD_TYPE_QUANTITY = 5
    private const val CARDS_QUANTITY = 200
    private val cardList = ArrayList<ShapeCard>()

    fun getShapeCardList(): List<ShapeCard> {
        cardList.clear()
        val random = Random(System.currentTimeMillis())
        var previousCardType = 100 //init value out of checking bounds
        for (i in 0..CARDS_QUANTITY) {
            val newLineOfCards = random.nextInt(CARD_LINE_LENGTH)
            val cardType = random.nextInt(CARD_TYPE_QUANTITY)
            for (j in 0..newLineOfCards) {
                val drawable = getCardShapeRes(cardType)
                val answer = getAnswer(j, cardType, previousCardType)
                cardList.add(ShapeCard(drawable, answer))
                previousCardType = cardType
            }
        }
        return cardList
    }

    private fun getAnswer(j: Int, cardType: Int, previousCardType: Int) =
        if ((j != 0) || (cardType == previousCardType)) Answer.YES
        else Answer.NO

    private fun getCardShapeRes(cardType: Int): Int =
        when (cardType) {
            0 -> R.drawable.shape_triangle
            1 -> R.drawable.shape_rectangle
            2 -> R.drawable.shape_circle
            3 -> R.drawable.shape_rhombus
            4 -> R.drawable.shape_star
            else -> R.drawable.shape_triangle
        }
}

data class ShapeCard(val shape: Int, val answer: Answer)

enum class Answer { YES, NO }