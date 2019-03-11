package com.sharipov.brainexercise.model.firebase

data class TestResult(
    var test: String = "",
    var date: Long = 0L,
    var score: Int = 0,
    var totalAnswers: Int = 0,
    var wrongAnswers: Int = 0
)