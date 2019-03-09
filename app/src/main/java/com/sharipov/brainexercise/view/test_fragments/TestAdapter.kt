package com.sharipov.brainexercise.view.test_fragments

interface TestAdapter {
    fun <T> getAnswer(position: Int): T
    fun resetList()
}