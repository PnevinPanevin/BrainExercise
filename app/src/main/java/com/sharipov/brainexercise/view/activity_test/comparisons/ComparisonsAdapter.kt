package com.sharipov.brainexercise.view.activity_test.comparisons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.Comparison
import com.sharipov.brainexercise.model.ComparisonAnswer
import kotlinx.android.synthetic.main.fragment_comparisons_item.view.*

class ComparisonsAdapter : RecyclerView.Adapter<ComparisonsAdapter.ComparisonViewHolder>() {

    lateinit var comparisons: List<Comparison>
    lateinit var onAnswerListener: OnComparisionAnswerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComparisonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_comparisons_item, parent, false)
        return ComparisonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComparisonViewHolder, position: Int) = with(holder.itemView) {
        val comparison = comparisons[position]
        firstExpression.text = comparison.first
        firstExpression.setOnClickListener { onAnswerListener.onAnswer(ComparisonAnswer.FIRST) }
        secondExpression.text = comparison.second
        secondExpression.setOnClickListener { onAnswerListener.onAnswer(ComparisonAnswer.SECOND) }
        containerView.setOnClickListener { onAnswerListener.onAnswer(ComparisonAnswer.EQUAL) }
    }

    override fun getItemCount(): Int = comparisons.size

    fun getAnswer(position: Int): ComparisonAnswer = comparisons[position].answer

    inner class ComparisonViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnComparisionAnswerListener {
        fun onAnswer(answer: ComparisonAnswer)
    }
}
