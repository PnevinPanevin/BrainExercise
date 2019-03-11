package com.sharipov.brainexercise.interactor

import com.google.firebase.database.FirebaseDatabase
import com.sharipov.brainexercise.model.firebase.TestResult

object ResultInteractor {
    const val EXPRESSIONS = "EXPRESSIONS"
    const val COMPARISONS = "COMPARISONS"
    const val SHAPES = "SHAPES"

    lateinit var userId: String

    fun putResults(testName: String, result: TestResult) {
        FirebaseDatabase.getInstance()
            .getReference(userId)
            .child(testName)
            .push()
            .setValue(result)
    }
}