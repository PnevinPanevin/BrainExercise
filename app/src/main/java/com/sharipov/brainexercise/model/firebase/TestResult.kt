package com.sharipov.brainexercise.model.firebase

data class TestResult(
    var test: String,
    var date: Long,
    var score: Int,
    var totalAnswers: Int,
    var wrongAnswers: Int
)