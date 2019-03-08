package com.sharipov.brainexercise.view.activity_test.comparisons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.Comparison
import com.sharipov.brainexercise.model.ComparisonAnswer
import kotlinx.android.synthetic.main.fragment_comparisons_item.view.*

class ComparisonItemFragment : Fragment() {

    lateinit var comparison: Comparison
    lateinit var listener: ComparisonsAdapter.OnComparisionAnswerListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_comparisons_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(view) {
        firstExpression.text = comparison.first
        firstExpression.setOnClickListener { listener.onAnswer(ComparisonAnswer.FIRST) }
        secondExpression.text = comparison.second
        secondExpression.setOnClickListener { listener.onAnswer(ComparisonAnswer.SECOND) }
        containerView.setOnClickListener { listener.onAnswer(ComparisonAnswer.EQUAL) }
    }
}