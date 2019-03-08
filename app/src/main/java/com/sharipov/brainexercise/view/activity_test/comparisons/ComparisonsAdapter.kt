package com.sharipov.brainexercise.view.activity_test.comparisons

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sharipov.brainexercise.model.Comparison
import com.sharipov.brainexercise.model.ComparisonAnswer

class ComparisonsAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    lateinit var comparisons: List<Comparison>
    lateinit var onAnswerListener: OnComparisionAnswerListener

    override fun getItem(position: Int): Fragment = ComparisonItemFragment().apply {
        this.comparison = comparisons[position]
        this.listener = onAnswerListener
    }

    override fun getCount(): Int = comparisons.size

    fun getAnswer(position: Int): ComparisonAnswer = comparisons[position].answer

    interface OnComparisionAnswerListener {
        fun onAnswer(answer: ComparisonAnswer)
    }
}
