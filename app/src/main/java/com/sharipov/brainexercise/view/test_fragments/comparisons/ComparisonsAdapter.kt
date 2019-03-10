package com.sharipov.brainexercise.view.test_fragments.comparisons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.repository.Comparison
import com.sharipov.brainexercise.model.repository.ComparisonAnswer
import com.sharipov.brainexercise.model.repository.ComparisonsRepository
import com.sharipov.brainexercise.view.test_fragments.TestAdapter
import kotlinx.android.synthetic.main.fragment_comparisons_item.view.*

class ComparisonsAdapter : RecyclerView.Adapter<ComparisonsAdapter.ComparisonViewHolder>(), TestAdapter {

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

    override fun <T> getAnswer(position: Int): T = comparisons[position].answer as T

    override fun resetList() {
        comparisons = ComparisonsRepository.getCardList()
        notifyDataSetChanged()
    }

    inner class ComparisonViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnComparisionAnswerListener {
        fun onAnswer(answer: ComparisonAnswer)
    }
}
