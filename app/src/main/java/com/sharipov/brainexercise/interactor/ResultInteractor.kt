package com.sharipov.brainexercise.interactor

import com.github.mikephil.charting.data.Entry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sharipov.brainexercise.model.firebase.TestResult

class ResultInteractor {
    companion object {
        const val EXPRESSIONS = "EXPRESSIONS"
        const val COMPARISONS = "COMPARISONS"
        const val SHAPES = "SHAPES"
        const val USER_ID = "DEFAULT_USER"
        const val RESULTS = "results"
    }

    lateinit var userId: String

    fun putResults(testName: String, result: TestResult) {
        FirebaseDatabase.getInstance()
            .getReference(RESULTS)
            .child("$userId/$testName")
            .push()
            .setValue(result)
    }

    fun getResults(
        testName: String,
        onSuccess: (List<Entry>) -> Unit,
        onError: (DatabaseError) -> Unit
    ) = FirebaseDatabase.getInstance()
        .getReference(RESULTS)
        .child("$userId/$testName")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) = onError(p0)

            override fun onDataChange(p0: DataSnapshot) = p0.children
                .mapNotNull { it.getValue<TestResult>(TestResult::class.java) }
                .map { Entry(it.date.toFloat(), it.score.toFloat()) }
                .run { onSuccess(this) }
        })
}